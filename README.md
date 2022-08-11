# Google Search Test

#### Search for Vodafone in google.com then validate number of results in pages 2 and 3 

The repo contains files for TC using selenium and testNG to search for Vodafone in google and validate the number of results in pages 2 and 3.

* Using Maven project and including all dependancies in POM.xml file.
* Using testNG to assert actions performed successfully.
* Using emailable-report.html for report from TC run by testNG.
* Using TestData.xlsx to provide data for TC script it contains (label,value) columns to mathc each label with its value.
* Using testNG.xml to configure and run TC file (googleSearch.java).

#### Run the project using testNG

- configure testNG.xml values to match the class name in our suite.
- Add data in TestData.xml file to be provided to test script.
- Run testNG.xml to run TC and find testNG result.
- Check test result to see its status (pass/fail).
- Check reports for more details about the TCs.
