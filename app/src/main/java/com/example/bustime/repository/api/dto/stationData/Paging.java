package com.example.bustime.repository.api.dto.stationData;

public class Paging
{
    private String firstRecordIndex;

    private String recordCountPerPage;

    private String lastRecordIndex;

    private String lastPageNo;

    private String pageSize;

    private String totalPageCount;

    private String firstPageNoOnPageList;

    private String lastPageNoOnPageList;

    private String firstPageNo;

    private String currentPageNo;

    private String totalRecordCount;

    public String getFirstRecordIndex ()
    {
        return firstRecordIndex;
    }

    public void setFirstRecordIndex (String firstRecordIndex)
    {
        this.firstRecordIndex = firstRecordIndex;
    }

    public String getRecordCountPerPage ()
    {
        return recordCountPerPage;
    }

    public void setRecordCountPerPage (String recordCountPerPage)
    {
        this.recordCountPerPage = recordCountPerPage;
    }

    public String getLastRecordIndex ()
    {
        return lastRecordIndex;
    }

    public void setLastRecordIndex (String lastRecordIndex)
    {
        this.lastRecordIndex = lastRecordIndex;
    }

    public String getLastPageNo ()
    {
        return lastPageNo;
    }

    public void setLastPageNo (String lastPageNo)
    {
        this.lastPageNo = lastPageNo;
    }

    public String getPageSize ()
    {
        return pageSize;
    }

    public void setPageSize (String pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getTotalPageCount ()
    {
        return totalPageCount;
    }

    public void setTotalPageCount (String totalPageCount)
    {
        this.totalPageCount = totalPageCount;
    }

    public String getFirstPageNoOnPageList ()
    {
        return firstPageNoOnPageList;
    }

    public void setFirstPageNoOnPageList (String firstPageNoOnPageList)
    {
        this.firstPageNoOnPageList = firstPageNoOnPageList;
    }

    public String getLastPageNoOnPageList ()
    {
        return lastPageNoOnPageList;
    }

    public void setLastPageNoOnPageList (String lastPageNoOnPageList)
    {
        this.lastPageNoOnPageList = lastPageNoOnPageList;
    }

    public String getFirstPageNo ()
    {
        return firstPageNo;
    }

    public void setFirstPageNo (String firstPageNo)
    {
        this.firstPageNo = firstPageNo;
    }

    public String getCurrentPageNo ()
    {
        return currentPageNo;
    }

    public void setCurrentPageNo (String currentPageNo)
    {
        this.currentPageNo = currentPageNo;
    }

    public String getTotalRecordCount ()
    {
        return totalRecordCount;
    }

    public void setTotalRecordCount (String totalRecordCount)
    {
        this.totalRecordCount = totalRecordCount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [firstRecordIndex = "+firstRecordIndex+", recordCountPerPage = "+recordCountPerPage+", lastRecordIndex = "+lastRecordIndex+", lastPageNo = "+lastPageNo+", pageSize = "+pageSize+", totalPageCount = "+totalPageCount+", firstPageNoOnPageList = "+firstPageNoOnPageList+", lastPageNoOnPageList = "+lastPageNoOnPageList+", firstPageNo = "+firstPageNo+", currentPageNo = "+currentPageNo+", totalRecordCount = "+totalRecordCount+"]";
    }
}