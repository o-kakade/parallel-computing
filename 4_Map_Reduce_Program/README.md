# U.S Census Diversity Index

Parallel Java Map Reduce (PJMR) job to analyze the census data and calculate the diversity index for every county in a given state or states for a given year. 

The program also calculates the diversity index for the overall population of each state.

Diversity Index for a population is the probability that two randomly chosen individuals in that population will be of different races.

This is calculated using 

<a href="https://www.codecogs.com/eqnedit.php?latex=D&space;=&space;\frac{1}{T^2}&space;\sum_{i=1}^{6}&space;N_{i}(T-&space;N_{i})" target="_blank"><img src="https://latex.codecogs.com/gif.latex?D&space;=&space;\frac{1}{T^2}&space;\sum_{i=1}^{6}&space;N_{i}(T-&space;N_{i})" title="D = \frac{1}{T^2} \sum_{i=1}^{6} N_{i}(T- N_{i})" /></a>


## Output Example

```
$ java pj2 jar=p4.jar DivIndex dr00,dr01,dr02,dr03,dr04,dr05 USCensus/cc-est2017-alldata.csv 10 "New York" "Delaware"
Delaware		0.45971
	New Castle County	0.50210
	Kent County	0.48199
	Sussex County	0.29902
New York		0.47584
	Queens County	0.65390
	Kings County	0.62253
	Bronx County	0.60372
	New York County	0.53530
	Westchester County	0.42755
	Nassau County	0.41871
	Richmond County	0.40180
	Albany County	0.39836
	Monroe County	0.38216
	Rockland County	0.37691
	Schenectady County	0.36599
	Erie County	0.34747
	Onondaga County	0.34385
	Tompkins County	0.33426
	Orange County	0.33248
	Dutchess County	0.32254
	Franklin County	0.28961
	Suffolk County	0.27567
	Sullivan County	0.26730
	Oneida County	0.25274
	Broome County	0.25194
	Rensselaer County	0.24370
	Jefferson County	0.23035
	Ulster County	0.22939
	Niagara County	0.22235
	Chemung County	0.21365
	Greene County	0.18810
	Orleans County	0.18458
	Columbia County	0.18228
	Seneca County	0.15810
	Putnam County	0.15491
	Clinton County	0.15324
	Cattaraugus County	0.15288
	Wyoming County	0.14762
	Cayuga County	0.14600
	Genesee County	0.13357
	Saratoga County	0.13325
	Montgomery County	0.12806
	Livingston County	0.12422
	Wayne County	0.12255
	Chautauqua County	0.12089
	St. Lawrence County	0.12029
	Ontario County	0.12029
	Essex County	0.11883
	Otsego County	0.11324
	Washington County	0.10925
	Cortland County	0.10132
	Madison County	0.10068
	Steuben County	0.096877
	Delaware County	0.091645
	Fulton County	0.091441
	Allegany County	0.085892
	Warren County	0.082873
	Schoharie County	0.081710
	Oswego County	0.075486
	Herkimer County	0.073554
	Hamilton County	0.069286
	Chenango County	0.067883
	Schuyler County	0.067763
	Tioga County	0.067176
	Yates County	0.060257
	Lewis County	0.057358
 
  ```
