function ajaxCall(startTime,endTime,startMin,endMin){
		let obj = {};
		obj.startTime = startTime + ":" + startMin + ":00";
		obj.endTime = endTime + ":" + endMin + ":00";
		
		$.ajax({
			type : "POST",
			url : "/temperature/api/temperature/average",
			async : true,
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			data : JSON.stringify(obj),
			
			beforeSend: function () {
				$.blockUI({ message: "<h1>查詢中 請稍等</h1>" });
				
				$.blockUI({ 
                    message: "<img src='/temperature/images/loader.jpg' />",
                    css: { backgroundColor: "white", color: "red" }
                }); 
				
	        },
			
			success : function(data) {
				if (data.result == "success") {
					$("#container").addClass("displayGraphics");
					highChartGraphics(data.avgTemperatureList);
				} else {
					alert(data.message);
				}
			},
			error : function(xhr, ajaxOption, error) {
				alert(xhr.responseText);
			},
			
			complete: function(XMLHttpRequest, textStatus) { 
				$.unblockUI();
			}
			
		});
	}