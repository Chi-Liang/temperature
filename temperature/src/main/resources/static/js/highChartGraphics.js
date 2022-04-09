function highChartGraphics(avgTemperatureList){
	

   let points = [];
   let linePoints = [];
   let categoriesText = [];
   
   $.each(avgTemperatureList, function(index,avgTemperature) {
	   let station = avgTemperature.station;
	   let averageList = avgTemperature.averageList;
	   $.each(averageList, function(index,average) {
		   if(station == "A"){
			   points.push([0, average]);
		   }
		   else if(station == "B"){
			   points.push([ 1, average]);
		   }
		   else if(station == "C"){
			   points.push([ 2, average]);
		   }
		   else if(station == "D"){
			   points.push([ 3, average]);
		   }
		   else if(station == "E"){
		       points.push([ 4, average]);
		   }
		  
	   });
   }); 
//   points.push([1,2,3,4,5]);
//   points.push([1,2,3,4,5]);
   
   //points = [1,2,3,4,5,6,7,8,9,10];
   
//   points.push([6,7,8,9,10]);
/**	for (let i = 0; i < avgTemperatureList.length; i++) {
		points.push([ 3, averageList[i] ]);
		linePoints.push([ i , averageList[i] ]);
		categoriesText.push("站別 :" + i);
	} */
	categoriesText.push("站別 :" + "A");
/**	categoriesText.push("站別 :" + "B");
	categoriesText.push("站別 :" + "C");
	categoriesText.push("站別 :" + "D");
	categoriesText.push("站別 :" + "E"); */
	
	console.log(points);
//	console.log(linePoints);
	
	let title = {
		text : "散點圖加折線圖"
	};

	let xAxis = {
			categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
	};
	

	let yAxis = {
		title : {
			text : "溫度"
		},
		min : 0
	};

	let series = [
		/**	{
				type : "line",
				name : "折線圖1",
				data : [ [ 0, 1.11 ], [ 1, 5.99 ], [ 2, 5.11 ],
						[ 3, 2.11 ], [ 4, 1.19 ], [ 5, 4.51 ], [ 6, 2.11 ],
						[ 7, 17.11 ], [ 8, 6.61 ], [ 9, 6.91 ],
						[ 10, 13.51 ] ],
				marker : {
					enabled : true
				},
				states : {
					hover : {
						lineWidth : 0
					}
				},
				enableMouseTracking : false
			}, 
			{
				type : "line",
				name : "折線圖2",
				data : linePoints,
				marker : {
					enabled : true
				},
				states : {
					hover : {
						lineWidth : 0
					}
				},
				enableMouseTracking : false
			},*/
			{
				type : "scatter",
				name : "散點圖",
				data : points,
				marker : {
					radius : 1,
					symbol : "circle",
					states : {
						hover : {
							enabled : true,
							lineColor : "rgb(100,100,100)"
						}
					}
				},
				states : {
					hover : {
						marker : {
							enabled : false
						}
					}
				},
				enableMouseTracking : false
			} ];

	let json = {};
	json.title = title;
	json.xAxis = xAxis;
	json.yAxis = yAxis;
	json.series = series;
	$('#container').highcharts(json);

}