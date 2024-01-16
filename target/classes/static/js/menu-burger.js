let b = true;
let burger = document.querySelector("#btn");
	let sidebar = document.querySelector(".sidebar");
	burger.onclick = function () {
		sidebar.classList.toggle("active");
		// if (sidebar.classList.contains("active")){
		// 	document.querySelector(".table-wrapper").style.width = "48.5%";
		// }
		// else {
		// 	document.querySelector(".table-wrapper").style.width = "42%";
		// }
		setTimeout(function(){
			$(".slider").slick('setPosition');
			$(".sliderbig").slick('setPosition')
		}, 470);

	}