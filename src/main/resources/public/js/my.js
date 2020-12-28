$(document).ready(function() {
	$("#nav-header .nav-link").click(function() {
		window.location.href = $(this).children("a").attr("href")
	});
	showActiveTab();
	
	
})

function showActiveTab() {
	$("#nav-header .nav-link a").each(function() {
		var remote = location.pathname;
		if (location.pathname != "/") {
			remote = location.pathname.substr(1);
		}
		var local = $(this).attr("href");
		local=local.substr(1);
		if(remote.startsWith("report"))
		{
			remote = remote.split("/");
			remote = remote[0];
		}
		if (remote=== local) {
			$(this).parent().addClass("navbar-header-active");
		}
	})
	$("#reportHeaderNav .nav-link").each(function() {
		var remote = location.pathname;
		if (location.pathname != "/") {
			remote = location.pathname.substr(1);
		}
		var local = $(this).attr("href");
		local=local.substr(1);
		if (remote=== local) {
			$(this).parent().addClass("navbar-header-active");
		}
		
	})
}
