function checkInput(startTime, endTime, startMin, endMin) {

	let errorMsg = "";
	if (!startTime) {
		$("#startDateShow").addClass("changeBorderColor");
		errorMsg += "請輸入開始日期\n";
	}
	if (!endTime) {
		$("#endDateShow").addClass("changeBorderColor");
		errorMsg += "請輸入結束日期\n";
	}

	if (!errorMsg) {
		let startDateTemp = startTime.split("/");
		let endDateTemp = endTime.split("/");
		let arrStartDate = startDateTemp[2].split(" ");
		let arrEndDate = endDateTemp[2].split(" ");

		let allStartDate = new Date(startDateTemp[0], startDateTemp[1],
				arrStartDate[0], arrStartDate[1], startMin);
		let allEndDate = new Date(endDateTemp[0], endDateTemp[1],
				arrEndDate[0], arrEndDate[1], endMin);
		if (allStartDate.getTime() >= allEndDate.getTime()) {
			$("#startDateShow").addClass("changeBorderColor");
			$("#endDateShow").addClass("changeBorderColor");
			errorMsg += "開始時間需晚於結束時間";
		}
	}
	return errorMsg;
}