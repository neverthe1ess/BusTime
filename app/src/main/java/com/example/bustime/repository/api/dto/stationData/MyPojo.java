package com.example.bustime.repository.api.dto.stationData;

public class MyPojo
{
    private String code;

    private Parameter parameter;

    private String count;

    private String description;

    private Paging paging;

    private String type;

    private Results[] results;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public Parameter getParameter ()
    {
        return parameter;
    }

    public void setParameter (Parameter parameter)
    {
        this.parameter = parameter;
    }

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public Paging getPaging ()
    {
        return paging;
    }

    public void setPaging (Paging paging)
    {
        this.paging = paging;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public Results[] getResults ()
    {
        return results;
    }

    public void setResults (Results[] results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", parameter = "+parameter+", count = "+count+", description = "+description+", paging = "+paging+", type = "+type+", results = "+results+"]";
    }
}