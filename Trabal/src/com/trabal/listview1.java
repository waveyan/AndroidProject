package com.trabal;

public class listview1 {
	private String name;
	private int imageId1, imageId2, imageId3, imageId4;

	public listview1(String name, int imageId1, int imageId2, int imageId3,
			int imageId4) {
		this.name = name;
		this.imageId1 = imageId1;
		this.imageId2 = imageId2;
		this.imageId3 = imageId3;
		this.imageId4 = imageId4;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId1() {
        return imageId1;
    }

    public void setImageId1(int imageId1) {
        this.imageId1 = imageId1;
    }

    public int getImageId2() {
        return imageId2;
    }

    public void setImageId2(int imageId2) {
        this.imageId2 = imageId2;
    }
    public int getImageId3() {
        return imageId3;
    }

    public void setImageId3(int imageId3) {
        this.imageId3 = imageId3;
    }
    public int getImageId4() {
        return imageId4;
    }

    public void setImageId4(int imageId4) {
        this.imageId4 = imageId4;
    }

}
