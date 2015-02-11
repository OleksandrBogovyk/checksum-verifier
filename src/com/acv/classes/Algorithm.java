package com.acv.classes;

import java.util.ArrayList;
import java.util.List;

public class Algorithm {

	public static List<Algorithm> getList() {
		List<Algorithm> list = new ArrayList<>();
		{
			Algorithm a = new Algorithm();
			a.setId("MD5");
			a.setTitle("MD5");
			a.setDescription("MD5 (Message Digest 5)");
			list.add(a);
		}
		{
			Algorithm a = new Algorithm();
			a.setId("SHA-1");
			a.setTitle("SHA-1");
			a.setDescription("SHA-1 (Secure Hash Algorithm)");
			list.add(a);
		}
		{
			Algorithm a = new Algorithm();
			a.setId("SHA-256");
			a.setTitle("SHA-256");
			a.setDescription("SHA-256 (Secure Hash Algorithm)");
			list.add(a);
		}
		{
			Algorithm a = new Algorithm();
			a.setId("SHA-512");
			a.setTitle("SHA-512");
			a.setDescription("SHA-512 (Secure Hash Algorithm)");
			list.add(a);
		}
		{
			Algorithm a = new Algorithm();
			a.setId("CRC32");
			a.setTitle("CRC32");
			a.setDescription("CRC32 (Cyclic Redundancy Check)");
			list.add(a);
		}
		{
			Algorithm a = new Algorithm();
			a.setId("Adler-32");
			a.setTitle("Adler-32");
			a.setDescription("Adler-32 (RFC 1950)");
			list.add(a);
		}
		return list;
	}

	@Override
	public String toString() {
		return title;
	}

	public boolean isCrc32() {
		return id.equals("CRC32");
	}

	public boolean isAdler32() {
		return id.equals("Adler-32");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String id = "";
	private String title = "";
	private String description = "";
}
