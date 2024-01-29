package com.example.bustime.repository.api.dto.stopData;

public class StopBusResults
{
    private String postPlateNo;

    private String govCd;

    private String remainStation;

    private String routeId;

    private String predictTm;

    private String govCdNm;

    private String plateNo;

    private String stationOrd;

    private String arrvVehId;

    private String routeNum;

    private String routeNm;

    private String via;

    public String getPostPlateNo ()
    {
        return postPlateNo;
    }

    public void setPostPlateNo (String postPlateNo)
    {
        this.postPlateNo = postPlateNo;
    }

    public String getGovCd ()
    {
        return govCd;
    }

    public void setGovCd (String govCd)
    {
        this.govCd = govCd;
    }

    public String getRemainStation ()
    {
        return remainStation;
    }

    public void setRemainStation (String remainStation)
    {
        this.remainStation = remainStation;
    }

    public String getRouteId ()
    {
        return routeId;
    }

    public void setRouteId (String routeId)
    {
        this.routeId = routeId;
    }

    public String getPredictTm ()
    {
        return predictTm;
    }

    public void setPredictTm (String predictTm)
    {
        this.predictTm = predictTm;
    }

    public String getGovCdNm ()
    {
        return govCdNm;
    }

    public void setGovCdNm (String govCdNm)
    {
        this.govCdNm = govCdNm;
    }

    public String getPlateNo ()
    {
        return plateNo;
    }

    public void setPlateNo (String plateNo)
    {
        this.plateNo = plateNo;
    }

    public String getStationOrd ()
    {
        return stationOrd;
    }

    public void setStationOrd (String stationOrd)
    {
        this.stationOrd = stationOrd;
    }

    public String getArrvVehId ()
    {
        return arrvVehId;
    }

    public void setArrvVehId (String arrvVehId)
    {
        this.arrvVehId = arrvVehId;
    }

    public String getRouteNum ()
    {
        return routeNum;
    }

    public void setRouteNum (String routeNum)
    {
        this.routeNum = routeNum;
    }

    public String getRouteNm ()
    {
        return routeNm;
    }

    public void setRouteNm (String routeNm)
    {
        this.routeNm = routeNm;
    }

    public String getVia ()
    {
        return via;
    }

    public void setVia (String via)
    {
        this.via = via;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [postPlateNo = "+postPlateNo+", govCd = "+govCd+", remainStation = "+remainStation+", routeId = "+routeId+", predictTm = "+predictTm+", govCdNm = "+govCdNm+", plateNo = "+plateNo+", stationOrd = "+stationOrd+", arrvVehId = "+arrvVehId+", routeNum = "+routeNum+", routeNm = "+routeNm+", via = "+via+"]";
    }
}

