package com.example.bustime.repository.api.dto.stationData;

public class Results
{
    private String govCd;

    private String stationEngNm;

    private String stationNm;

    private String govCdNm;

    private String mobiNum;

    private String useYn;

    private String gpsX;

    private String stationId;

    private String isBit;

    private String gpsY;

    public String getGovCd ()
    {
        return govCd;
    }

    public void setGovCd (String govCd)
    {
        this.govCd = govCd;
    }

    public String getStationEngNm ()
    {
        return stationEngNm;
    }

    public void setStationEngNm (String stationEngNm)
    {
        this.stationEngNm = stationEngNm;
    }

    public String getStationNm ()
    {
        return stationNm;
    }

    public void setStationNm (String stationNm)
    {
        this.stationNm = stationNm;
    }

    public String getGovCdNm ()
    {
        return govCdNm;
    }

    public void setGovCdNm (String govCdNm)
    {
        this.govCdNm = govCdNm;
    }

    public String getMobiNum ()
    {
        return mobiNum;
    }

    public void setMobiNum (String mobiNum)
    {
        this.mobiNum = mobiNum;
    }

    public String getUseYn ()
    {
        return useYn;
    }

    public void setUseYn (String useYn)
    {
        this.useYn = useYn;
    }

    public String getGpsX ()
    {
        return gpsX;
    }

    public void setGpsX (String gpsX)
    {
        this.gpsX = gpsX;
    }

    public String getStationId ()
    {
        return stationId;
    }

    public void setStationId (String stationId)
    {
        this.stationId = stationId;
    }

    public String getIsBit ()
    {
        return isBit;
    }

    public void setIsBit (String isBit)
    {
        this.isBit = isBit;
    }

    public String getGpsY ()
    {
        return gpsY;
    }

    public void setGpsY (String gpsY)
    {
        this.gpsY = gpsY;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [govCd = "+govCd+", stationEngNm = "+stationEngNm+", stationNm = "+stationNm+", govCdNm = "+govCdNm+", mobiNum = "+mobiNum+", useYn = "+useYn+", gpsX = "+gpsX+", stationId = "+stationId+", isBit = "+isBit+", gpsY = "+gpsY+"]";
    }
}
