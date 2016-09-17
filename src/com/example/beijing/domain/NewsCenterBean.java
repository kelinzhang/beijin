package com.example.beijing.domain;

import java.util.List;

public class NewsCenterBean {
  public int retcode;
  public List<NewsCeterData> data;
  public List extend;
  public class NewsCeterData{
	
	  public List<Children> children;
	  public int id;
	  public String title;
	  public int type;
	  public String url;
	  public String url1;
	  public String dayurl;
	  public String excurl;
	  public String weekurl;
  }
  public class Children{
	  public int id;
	  public int type;
	  public String title;
	  public String url;
  }
}
