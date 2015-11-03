package videoencoder.beans;

public class ZencoderResponse {
	private String id;
	private ZencoderOutput[] outputs;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ZencoderOutput[] getOutputs() {
		return outputs;
	}
	public void setOutputs(ZencoderOutput[] outputs) {
		this.outputs = outputs;
	}
	
}
