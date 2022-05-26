package com.timcast.flurbovurbo.service;

import java.util.List;

import com.timcast.flurbovurbo.model.Flurbo;
import com.timcast.flurbovurbo.model.Vurbo;

public interface CacheService {

	public List<Vurbo> getAllVurbos();
	
	public List<Flurbo> getAllFlurbos();
	
	public List<Vurbo> getAllVurbosForFlurbo(int flurboId);
	
	public String getCurrentVurboDocument(int flurboId);
	
	public void appendVurboToFlurbo(String sessionId, int flurboId, String vurbo);	
	
	public void flushCurrentVurboDocument(int flurboId);
}
