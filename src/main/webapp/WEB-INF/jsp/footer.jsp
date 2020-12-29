		</div>
	</div>	
	
<footer class="blog-footer">
      <p>Copyright © 2020 Nigam Patel | 7575046524</p>
      
    </footer>	
</body>
<script src="/js/my.js"></script>
<script>
	if("${message}")
	{
		$.toast({
			title : 'Success',
			content : "${message}",
			type : 'success',
			delay : 5000
		});
	}
	if("${error}")
	{
		$.toast({
			title : 'Error',
			content : "${error}",
			type : 'error',
			delay : 5000
		});
	}
</script>
</html>