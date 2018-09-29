<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>News Search</title>


<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="stylesheet" type="text/css"
	href="bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="font-awesome/css/font-awesome.min.css" />


<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>

<style>
.faqHeader {
	font-size: 27px;
	margin: 20px;
}

.panel-heading [data-toggle="collapse"]:after {
	font-family: 'Glyphicons Halflings';
	content: "\e072"; /* "play" icon */
	float: right;
	color: #F58723;
	font-size: 18px;
	line-height: 22px;
	/* rotate "play" icon from > (right arrow) to down arrow */
	-webkit-transform: rotate(-90deg);
	-moz-transform: rotate(-90deg);
	-ms-transform: rotate(-90deg);
	-o-transform: rotate(-90deg);
	transform: rotate(-90deg);
}

.panel-heading [data-toggle="collapse"].collapsed:after {
	/* rotate "play" icon from > (right arrow) to ^ (up arrow) */
	-webkit-transform: rotate(90deg);
	-moz-transform: rotate(90deg);
	-ms-transform: rotate(90deg);
	-o-transform: rotate(90deg);
	transform: rotate(90deg);
	color: #454444;
}

.form-wrapper {
	background-image: -webkit-linear-gradient(top, #f6f6f6, #eae8e8);
	background-image: -moz-linear-gradient(top, #f6f6f6, #eae8e8);
	background-image: -ms-linear-gradient(top, #f6f6f6, #eae8e8);
	background-image: -o-linear-gradient(top, #f6f6f6, #eae8e8);
	background-image: linear-gradient(top, #f6f6f6, #eae8e8);
	border-color: #dedede #bababa #aaa #bababa;
	border-style: solid;
	border-width: 1px;
	-webkit-border-radius: 10px;
	-moz-border-radius: 10px;
	border-radius: 10px;
	-webkit-box-shadow: 0 3px 3px rgba(255, 255, 255, 0.1), 0 3px 0 #bbb, 0
		4px 0 #aaa, 0 5px 5px #444;
	-moz-box-shadow: 0 3px 3px rgba(255, 255, 255, 0.1), 0 3px 0 #bbb, 0 4px
		0 #aaa, 0 5px 5px #444;
	box-shadow: 0 3px 3px rgba(255, 255, 255, 0.1), 0 3px 0 #bbb, 0 4px 0
		#aaa, 0 5px 5px #444;
	margin: 5% auto;
	overflow: hidden;
	padding: 8px;
	width: 50%;
}

#searchterm {
	border: 1px solid #ccc;
	-webkit-box-shadow: 0 1px 1px #ddd inset, 0 1px 0 #fff;
	-moz-box-shadow: 0 1px 1px #ddd inset, 0 1px 0 #fff;
	box-shadow: 0 1px 1px #ddd inset, 0 1px 0 #fff;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	color: #999;
	float: left;
	height: 20px;
	padding: 10px;
	width: 80%;
	height: 50%;
}

#searchterm:focus {
	border-color: #aaa;
	-webkit-box-shadow: 0 1px 1px #bbb inset;
	-moz-box-shadow: 0 1px 1px #bbb inset;
	box-shadow: 0 1px 1px #bbb inset;
	outline: 0;
}

#submit {
	font-color: white;
	background-color: #F58723;
	background-image: -webkit-gradient(linear, left top, left bottom, from(#F58723),
		to(#d36f13));
	background-image: -webkit-linear-gradient(top, #F58723, #d36f13);
	background-image: -moz-linear-gradient(top, #F58723, #d36f13);
	background-image: -ms-linear-gradient(top, #F58723, #d36f13);
	background-image: -o-linear-gradient(top, #F58723, #d36f13);
	background-image: linear-gradient(top, #F58723, #d36f13);
	border: 1px solid #00748f;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	border-radius: 3px;
	-webkit-box-shadow: 0 1px 0 rgba(255, 255, 255, 0.3) inset, 0 1px 0 #fff;
	-moz-box-shadow: 0 1px 0 rgba(255, 255, 255, 0.3) inset, 0 1px 0 #fff;
	box-shadow: 0 1px 0 rgba(255, 255, 255, 0.3) inset, 0 1px 0 #fff;
	cursor: pointer;
	height: 42px;
	float: right;
	padding: 0;
	width: 100px;
}

#submit:hover, #submit:focus {
	background-color: #F58723;
	background-image: -webkit-gradient(linear, left top, left bottom, from(#F58723),
		to(#d36f13));
	background-image: -webkit-linear-gradient(top, #d36f13, #F58723);
	background-image: -moz-linear-gradient(top, #d36f13, #F58723);
	background-image: -ms-linear-gradient(top, #d36f13, #F58723);
	background-image: -o-linear-gradient(top, #d36f13, #F58723);
	background-image: linear-gradient(top, #d36f13, #F58723);
}

#submit:active {
	-webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
	-moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
	box-shadow: 0 1px 4px rgba(0, 0, 0, 0.5) inset;
	outline: 0;
}

#submit::-moz-focus-inner {
	border: 0;
}

body {
	background: url(img/bg.jpg) no-repeat;
	background-attachment: fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
	opacity: 5%;
}
</style>


</head>
<body bgcolor="white" class="bod">
	<div class="searcher">
		<div class="page-header" style="text-align: center;">
			<h1>
				<a href="" style="text-decoration: none"> NewsPedia <small>News
						search engine</small></a>
			</h1>
		</div>
		<form action="search.do" name="search" id="search" method="post"
			class="form-wrapper">

			<table height=100% width=100%>
				<tr>
					<td>
						<div style="text-align: center">
							<input type="text" name="searchterm" id="searchterm"
								placeholder="Search for...">
							<button type="submit" id="submit" style="color: white">Search</button>
							<!--  <button type="reset" value="Clear"></button> -->
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div>
							<label><input type="radio" name="group1" value="opt1"
								width=50% checked="checked"> Keyword search results</label>

							<label><input type="radio" name="group1" value="opt2"
								width=50%> Semantic search results</label>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="container" height=100% width=100%>

		<div class="panel-group" id="accordion">

			<table>
				<tr>
					<td>

						<div class="result" id="result"></div>
					</td >
					<td>
						<div class="result1" id="result1" style="display: none;"></div>


					</td>
				</tr>
			</table>


		</div>
		<div class="alert alert-warning alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			Tip: A blank search returns 10 latest news articles from the
			collection
		</div>

		<br />
	</div>


	<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("input[name=group1]").on("change", function() {

				var test = $(this).val();
				if(test == 'opt1') {
					$("#result").show();
					$("#result1").hide();      
					$("#resTitle1").show();   
					$("#resTitle2").hide();  
		       }

		       else {
					$("#result").hide();
					$("#result1").show();
					$("#resTitle1").hide(); 
					$("#resTitle2").show();  
		       }

			});
		});

		var form = $('#search');

		form
				.submit(function() {

					$
							.ajax({
								type : form.attr('method'),
								url : form.attr('action'),
								data : form.serialize(),
								success : function(data) {
									response = $.parseJSON(data);
									$('#result').empty();
									$('#result1').empty();
									$(function() {
											  
										var coll = ''
										var rec = ''
										$("#result")
												.append(
														'')
										$
												.each(
														response,
														function(i, item) {
															if (i < 10) {
																if (i == 0) {
																	coll = ''
																	rec = ' in'
																} else {
																	coll = ' collapsed'
																	rec = ''
																}
																$("#result")
																		.append(
																				'<div class="panel panel-default">'
																						+ '<div class="panel-heading">'
																						+ '<h4 class="panel-title">'
																						+ '<a class="accordion-toggle'+coll+' " data-toggle="collapse"'
																					+'data-parent="#accordion" href="#collapse'+i+'">'
																						+ item.title
																						+ '</a></h4>'
																						+ '</div><div id="collapse'+i +'"class="panel-collapse collapse'+rec+'">'
																						+ '<div class="panel-body">'
																						+ '<p style="text-align: right;"> Relevance : '
																						+ -
																						+ '%</p>'
																						+ '<p style="text-align: right;"> By: '
																						+ item.publication
																						+ ' . Date: '
																						+ item.date
																						+ '</p>'
																						+ item.content
																						+ '</div></div></div>')

																//.appendTo('#records_table');
																//  console.log($tr.wrap('<p>').html());

															} else {
																if (i == 10) {
																	coll = ''
																	rec = ' in'
																} else {
																	coll = ' collapsed'
																	rec = ''
																}
																$("#result1")
																		.append(
																				'<div class="panel panel-default">'
																						+ '<div class="panel-heading">'
																						+ '<h4 class="panel-title">'
																						+ '<a class="accordion-toggle'+coll+' " data-toggle="collapse"'
																					+'data-parent="#accordion" href="#collapse'+i+'">'
																						+ item.title
																						+ '</a></h4>'
																						+ '</div><div id="collapse'+i +'"class="panel-collapse collapse'+rec+'">'
																						+ '<div class="panel-body">'
																						+ '<p style="text-align: right;"> Relevance : '
																						+ -
																						+ '%</p>'
																						+ '<p style="text-align: right;"> By: '
																						+ item.publication
																						+ ' . Date: '
																						+ item.date
																						+ '</p>'
																						+ item.content
																						+ '</div></div></div>')
															}

														}

												);
									});
								}
							});

					return false;
				});
	</script>

</body>
</html>