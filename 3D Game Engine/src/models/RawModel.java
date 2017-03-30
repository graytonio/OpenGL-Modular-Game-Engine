package models;

public class RawModel {

	private int vaoID;
	private int vertexCount;
	private float[] positions;
	private float[] textureCoords;
	private float[] normals;
	private int[] indices;
	
	public RawModel(int vaoID, int vertexCount){
		this.vaoID=vaoID;
		this.vertexCount=vertexCount;
	}

	public RawModel(int vaoID, int vertexCount, float[] positions, float[] textureCoords, float[] normals, int[] indices) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.positions=positions;
		this.textureCoords=textureCoords;
		this.normals=normals;
		this.indices=indices;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public float[] getPositions() {
		return positions;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public float[] getNormals() {
		return normals;
	}
	
	public int[] getIndices(){
		return indices;
	}
}
