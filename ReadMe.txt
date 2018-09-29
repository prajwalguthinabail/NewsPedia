Requirements:
1. Java 1.8 or 1.9
2. Apache Tomcat server 7.0
3. Heap memory of 8GB might be needed
4. Latest version of Google Chrome, Mozilla Firefox, or Microsoft Edge
5. Eclipse IDE JEE edition

How to run:

NOTE:Step 1 and 2 are data creation.

1. Run TermFrequencyWriter as a standard Java application. This writes original term matrix to a text file.

2. Run MatrixWriter as a standard Java application. This writes the reduced SVD matrices to a file. Note that this process takes about 8-10 GB of memory space and around 20 minutes of execution.

3. This is for the first time only. Later just skip to step 4. 
Open MainSearch.jsp in Eclipse and select > Run > Run configurations > Apache Tomcat > Righ click to select New > Arguments tab > Working Directory > Select Other > Click Workspace >  Run 

4. Once the data files are in place, The web application can be executed by running the MainSearch.jsp on Tomcat server. The URL to use in a browser is given below. 

http://localhost:8080/NewsPedia/MainSearch.jsp

4. It takes a while to load txt files for the first search query. Subsequent search requests are faster.
