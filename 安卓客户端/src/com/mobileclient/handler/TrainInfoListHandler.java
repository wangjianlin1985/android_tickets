package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.TrainInfo;
public class TrainInfoListHandler extends DefaultHandler {
	private List<TrainInfo> trainInfoList = null;
	private TrainInfo trainInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (trainInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("seatId".equals(tempString)) 
            	trainInfo.setSeatId(new Integer(valueString).intValue());
            else if ("trainNumber".equals(tempString)) 
            	trainInfo.setTrainNumber(valueString); 
            else if ("startStation".equals(tempString)) 
            	trainInfo.setStartStation(new Integer(valueString).intValue());
            else if ("endStation".equals(tempString)) 
            	trainInfo.setEndStation(new Integer(valueString).intValue());
            else if ("startDate".equals(tempString)) 
            	trainInfo.setStartDate(Timestamp.valueOf(valueString));
            else if ("seatType".equals(tempString)) 
            	trainInfo.setSeatType(new Integer(valueString).intValue());
            else if ("price".equals(tempString)) 
            	trainInfo.setPrice(new Float(valueString).floatValue());
            else if ("seatNumber".equals(tempString)) 
            	trainInfo.setSeatNumber(new Integer(valueString).intValue());
            else if ("leftSeatNumber".equals(tempString)) 
            	trainInfo.setLeftSeatNumber(new Integer(valueString).intValue());
            else if ("startTime".equals(tempString)) 
            	trainInfo.setStartTime(valueString); 
            else if ("endTime".equals(tempString)) 
            	trainInfo.setEndTime(valueString); 
            else if ("totalTime".equals(tempString)) 
            	trainInfo.setTotalTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("TrainInfo".equals(localName)&&trainInfo!=null){
			trainInfoList.add(trainInfo);
			trainInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		trainInfoList = new ArrayList<TrainInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("TrainInfo".equals(localName)) {
            trainInfo = new TrainInfo(); 
        }
        tempString = localName; 
	}

	public List<TrainInfo> getTrainInfoList() {
		return this.trainInfoList;
	}
}
