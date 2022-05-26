package com.timcast.flurbovurbo.service;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timcast.flurbovurbo.model.Flurbo;
import com.timcast.flurbovurbo.model.Vurbo;
import com.timcast.flurbovurbo.repository.FlurboRepository;
import com.timcast.flurbovurbo.repository.VurboRepository;

@Service
public class CacheServiceImpl implements CacheService {

	private static Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);
	
	@Autowired
	private FlurboRepository flurbosRepo;
	
	@Autowired
	private VurboRepository vurbosRepo;
	
	public List<Vurbo> getAllVurbos() {
		logger.info("getting all vurbos");
		return vurbosRepo.findAll();
	}
	
	public List<Flurbo> getAllFlurbos() {
		logger.info("getting all flurbos");
		return flurbosRepo.findAll();
	}
	
	public List<Vurbo> getAllVurbosForFlurbo(int flurboId) {
		logger.info("getting all vurbos for flurbo: [" + flurboId + "]");
		return vurbosRepo.findByFlurboId(flurboId);
	}
	
	public String getCurrentVurboDocument(int flurboId) {
		
		logger.info("getting Document for flurbo: [" + flurboId + "]");
		
		List<Vurbo> vurbos = getAllVurbosForFlurbo(flurboId);
		
		StringBuffer flurboDoc = new StringBuffer();
		
		for (Vurbo vurbo : vurbos) {
			if (vurbo.isActiveIndicator()) {
				flurboDoc.append(vurbo.getVurbo());
				flurboDoc.append(" ");
			}
		}
		
		return flurboDoc.toString();
		
	}
	
	public void flushCurrentVurboDocument(int flurboId) {
		
		logger.info("getting Document for flurbo: [" + flurboId + "]");
		
		List<Vurbo> vurbos = getAllVurbosForFlurbo(flurboId);
		
		for (Vurbo vurbo : vurbos) {
			vurbo.setActiveIndicator(false);
		    vurbosRepo.save(vurbo);	
		}
		
		
	}
	
	public void appendVurboToFlurbo(String sessionId, int flurboId, String vurbo) {
		
		Vurbo v = new Vurbo();
		
		v.setActiveIndicator(true);
		v.setAuthorId(1);
		v.setCreateDate(new Date(System.currentTimeMillis()));
		v.setModifiedDate(new Date(System.currentTimeMillis()));
		v.setFlurboId(flurboId);
	    v.setIpAddress("1.2.3.4");
	    v.setVurbo(vurbo);
	    
	    vurbosRepo.save(v);
	}

}
