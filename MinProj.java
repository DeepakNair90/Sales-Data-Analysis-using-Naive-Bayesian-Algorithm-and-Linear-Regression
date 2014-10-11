import java.io.*;
import java.util.*;
import java.sql.*;
class MinProj
{
	public static void main(String args[])
	{
		Scanner src=new Scanner(System.in);
		 
	 Connection con4 = null;

  String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
   
  try {
  Class.forName(driver);
  
  con4 = DriverManager.getConnection("jdbc:odbc:bi","sa","");
  
  System.out.println("Connection created with database");
  
   Statement db4 = con4.createStatement();
   

      ResultSet rs = db4.executeQuery("select count(revenue) from salesdata");
      rs.next();
 
     
      int n=rs.getInt(1);
      int i=0,xmean,ymean,sum1=0,sum2=0,sum3=0,sum4=0;
      
      int  x[]=new int[n];
	  int y[]=new int[n];
	  
	  ResultSet rs1=db4.executeQuery("select * from salesdata");;

while(rs1.next())
{
	x[i]=rs1.getInt(1);
	y[i]=rs1.getInt(2);
	i++;	
}
  
/*for(i=0;i<n;i++)
  {
  	System.out.println(x[i]+" "+y[i]);
  }*/
  
  for( i=0;i<n;i++)
		{
			sum1=sum1+x[i];
		}
		xmean=sum1/n;
			for( i=0;i<n;i++)
		{
			sum2=sum2+y[i];
		}
		ymean=sum2/n;
			for( i=0;i<n;i++)
		{
		sum3=sum3+((x[i]-xmean)*(y[i]-ymean));
		sum4=sum4+((x[i]-xmean)*(x[i]-xmean));
		}
		double w1=sum3/sum4;
		double w0=ymean-(w1*xmean);
		System.out.println("Enter the value for sales for year1 :");
		int z=src.nextInt();
		double yy=w0+w1*z;
		System.out.println("Year2 sales predicted by linear regression is:"+yy);
	
	//db4.execute("insert into salesdata values("+z+","+yy+",'Unknown')");
		
		
 ResultSet rs2 = db4.executeQuery("select count(revenue) from salesdata where revenue='good'");
 rs2.next();
  
 float good=rs2.getFloat(1);
 
 rs2 = db4.executeQuery("select count(revenue) from salesdata where revenue='poor'");
 rs2.next();
  
 float poor=rs2.getFloat(1);
   
 rs2= db4.executeQuery("select count(year1) from salesdata where revenue='good' and year1="+z+"");
 rs2.next();
 
 float zg=rs2.getFloat(1);
 
 rs2= db4.executeQuery("select count(year1) from salesdata where revenue='good' and year1="+z+"");
 rs2.next();
 
 float yyg=rs2.getFloat(1);
 
 
 
 
 rs2= db4.executeQuery("select count(year1) from salesdata where revenue='poor' and year1="+z+"");
 rs2.next();
 
 float zp=rs2.getFloat(1);
 
 rs2= db4.executeQuery("select count(year1) from salesdata where revenue='poor' and year1="+z+"");
 rs2.next();
 
 float yyp=rs2.getFloat(1);
 
 
  float a=(zg/good)*(yyg/good)*(good/(good+poor));
  
  float b=(zp/poor)*(yyp/poor)*(good/(good+poor));

 if(a>b)
 {
 	System.out.println("Revenue for this sales statistic is good as per Naive Bayesian classification");
 	db4.execute("insert into salesdata values("+z+","+yy+",'good')");
 }
 else{
 	System.out.println("Revenue for this sales statistic is poor as per Naive Bayesian classification");
 	db4.execute("insert into salesdata values("+z+","+yy+",'poor')");
 }
 
 System.out.println("Database Updated"); 
}



catch (Exception e) {
System.out.println(e);
  } 
  }
}

  
  
  
  
  
  
