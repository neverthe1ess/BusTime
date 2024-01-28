package com.example.bustime.repository.api.dto.stationData;

public class Parameter
{
    private String govCd;

    private String firstIndex;

    private String pageIndex;

    private String pageSize;

    private String pageUnit;

    private String lastIndex;

    private String type;

    private String stationId;

    public String getGovCd ()
    {
        return govCd;
    }

    public void setGovCd (String govCd)
    {
        this.govCd = govCd;
    }

    public String getFirstIndex ()
    {
        return firstIndex;
    }

    public void setFirstIndex (String firstIndex)
    {
        this.firstIndex = firstIndex;
    }

    public String getPageIndex ()
    {
        return pageIndex;
    }

    public void setPageIndex (String pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    public String getPageSize ()
    {
        return pageSize;
    }

    public void setPageSize (String pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getPageUnit ()
    {
        return pageUnit;
    }

    public void setPageUnit (String pageUnit)
    {
        this.pageUnit = pageUnit;
    }

    public String getLastIndex ()
    {
        return lastIndex;
    }

    public void setLastIndex (String lastIndex)
    {
        this.lastIndex = lastIndex;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getStationId ()
    {
        return stationId;
    }

    public void setStationId (String stationId)
    {
        this.stationId = stationId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [govCd = "+govCd+", firstIndex = "+firstIndex+", pageIndex = "+pageIndex+", pageSize = "+pageSize+", pageUnit = "+pageUnit+", lastIndex = "+lastIndex+", type = "+type+", stationId = "+stationId+"]";
    }
}