/**
 * 
 */
var app = angular.module('videoEncoder', ['angularFileUpload']);

app.controller('HomeController', ['$scope', 'FileUploader', function ($scope, FileUploader) {
	$scope.uploadMessage = 'Arraste aqui o arquivo que deseja converter';
	$scope.fileEncoded = false;
	$scope.encodedFileUrl = '';
	
	var uploader = $scope.uploader = new FileUploader( { autoUpload: true, url: '/VideoEncoder/Encode'});
	
	// FILTERS

    uploader.filters.push({
        name: 'customFilter',
        fn: function(item /*{File|FileLikeObject}*/, options) {
            return this.queue.length <= 1;
        }
    });
	
	// Callbacks
	uploader.onAfterAddingFile = function (item) {
		$scope.uploadMessage = 'Enviando arquivo ' + item.file.name.toString();
	};
	
	uploader.onCompleteItem = function(item, response, status, headers) {
		if (response && response.encodedFile) {
			$scope.encodedFileUrl = response.encodedFile;
			$scope.fileEncoded = true;
		}
	};
}]);