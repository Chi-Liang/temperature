$(function() {
		$('#startDate,#endDate').datetimepicker({
			"dateFormat" : "yy/mm/dd",
			"timeFormat" : "HH",
			"hour" : "08",
			"yearRange" : "-10:+10",
			"changeMonth" : true,
			"changeYear" : true,
			"defaultDate": "2021/12/28"
		});
		
		$("#startDate").change(function(){
			$("#startDateShow").val($("#startDate").val());
		});
		
		$("#endDate").change(function(){
			$("#endDateShow").val($("#endDate").val());
		});
		
		$("#startDateShow").attr("disabled",true);
		$("#endDateShow").attr("disabled",true);
		
		$("#container").addClass("hideGraphics");
		$("#startDate").addClass("pictureWidth");
		$("#endDate").addClass("pictureWidth");
		
		$( "#btn" ).click(function() {
			
			$("#startDateShow").removeClass("changeBorderColor");
			$("#endDateShow").removeClass("changeBorderColor");
			$("#container").removeClass("displayGraphics");
			$("#container").addClass("hideGraphics");
			
			let startTime = $("#startDate").val();
			let endTime = $("#endDate").val();
			let startMin = $('#startMin').val();
			let endMin = $('#endMin').val();
			let errorMsg = checkInput(startTime,endTime,startMin,endMin);
			if(errorMsg){
				alert(errorMsg);
				return;
			}
			
			ajaxCall(startTime,endTime,startMin,endMin);
			
		});	
		
	});