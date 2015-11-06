'use strict';

var app = angular.module('videoEncoder', [
                                          'angularFileUpload',
                                          'ngSanitize',
                                          'com.2fdevs.videogular'
                                          ]);


app.filter('isEmpty', function() {
	return function(obj) {
		return obj === '';
	}
});

app.controller('HomeController', ['$scope', '$http', '$sce', 'FileUploader', function ($scope, $http, $sce, FileUploader) {
	var ctrl = this;
	
	$scope.encoding = {
			isRunning: false,
			hasFinished: false,
			fileUrl: ''
	};
	
	ctrl.config = {
            preload: "none",
            sources: [],
            tracks: [], 
            theme: {
            	url: 'http://www.videogular.com/styles/themes/default/latest/videogular.css'
            	}
            };
	
	
	var uploader = $scope.uploader = new FileUploader( { autoUpload: true, url: 'Encode'});
	
	// Filters
    uploader.filters.push({
        name: 'customFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            return this.queue.length <= 1;
        }
    });
	
	// Callbacks
	uploader.onAfterAddingFile = function (item) {
		$scope.fileName = item.file.name.toString();
	};
	
	uploader.onSuccessItem = function (item, response, status, headers) {
		if (response && response.encodedFile) {
			$scope.encoding.isRunning = true;
			$scope.encoding.hasFinished = false;
			$scope.uploadMessage = 'Aguarde. O arquivo est� sendo convertido!';			
			
			ctrl.requestStatus(response.encodedFile);
		}
	};
	
	ctrl.requestStatus = function (jobId) {
		$http({
			method: 'GET',
			url: 'https://app.zencoder.com/api/v2/jobs/' + jobId + '.json?api_key=32c53c9fda593a7f91dfa11ca2e3f41b'
		}).then(function successCallback(response) {
			var job = response.data.job;
			if (job.state === 'finished') {
				// Indico que a convers�o j� finalizou e carrego a exibição
				$scope.encoding.isRunning = false;
				$scope.encoding.hasFinished = true;
				
				ctrl.config.sources = [
				                       {src: $sce.trustAsResourceUrl(job.output_media_files[0].url), type: "video/mp4"}
				                       ];
				
				$scope.uploadMessage = '';
			}
			else if (job.state === 'cancelled' 
				|| job.state == 'failed') {
				$scope.uploadMessage = 'Ops! A conversão não foi concluída devido a algum problema.';
				
				$scope.encoding.isRunning = false;
				$scope.encoding.hasFinished = false;
			}
			else {
				$scope.encoding.isRunning = true;
				$scope.encoding.hasFinished = false;
				
				// Aguardo 5 segundos pra consultar o status da conversão novamente
				setTimeout(function() {
					ctrl.requestStatus(jobId);
				}, 5000);
			}
			
		}, function errorCallback(response) {
			$scope.uploadMessage = 'Ops! A conversão não foi concluída devido a algum problema.';
			
			$scope.encoding.isRunning = false;
			$scope.encoding.hasFinished = false;
		});
	};
}]);