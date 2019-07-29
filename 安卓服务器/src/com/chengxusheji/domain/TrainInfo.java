package com.chengxusheji.domain;

import java.sql.Timestamp;
public class TrainInfo {
    /*记录编号*/
    private int seatId;
    public int getSeatId() {
        return seatId;
    }
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    /*车次*/
    private String trainNumber;
    public String getTrainNumber() {
        return trainNumber;
    }
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    /*始发站*/
    private StationInfo startStation;
    public StationInfo getStartStation() {
        return startStation;
    }
    public void setStartStation(StationInfo startStation) {
        this.startStation = startStation;
    }

    /*终到站*/
    private StationInfo endStation;
    public StationInfo getEndStation() {
        return endStation;
    }
    public void setEndStation(StationInfo endStation) {
        this.endStation = endStation;
    }

    /*开车日期*/
    private Timestamp startDate;
    public Timestamp getStartDate() {
        return startDate;
    }
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /*席别*/
    private SeatType seatType;
    public SeatType getSeatType() {
        return seatType;
    }
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    /*票价*/
    private float price;
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    /*总座位数*/
    private int seatNumber;
    public int getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    /*剩余座位数*/
    private int leftSeatNumber;
    public int getLeftSeatNumber() {
        return leftSeatNumber;
    }
    public void setLeftSeatNumber(int leftSeatNumber) {
        this.leftSeatNumber = leftSeatNumber;
    }

    /*开车时间*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*终到时间*/
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /*历时*/
    private String totalTime;
    public String getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

}