package com.example.bustime.repository.api.dto.stopData;

import java.util.Arrays;

public class StopPostResults
{
    private String code;

    private Parameter parameter;

    private String count;

    private String description;

    private String type;

    private StopBusResults[] results;

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

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public StopBusResults[] getResults ()
    {
        return results;
    }

    public void setResults (StopBusResults[] results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", parameter = "+parameter+", count = "+count+", description = "+description+", type = "+type+", results = "+ Arrays.toString(results)+ "]";
    }
}