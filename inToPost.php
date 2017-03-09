<?php
	
	$infix="";
	if(isset($_GET["infix"]))
		$infix=$_GET["infix"];

	$stack=new SplStack();
	$infix_arr=str_split($infix);
	$postfix=array();
	
	$operators=array(
			"("=>2,
			"."=>0,
			"+"=>1,
			")"=>3,
			"*"=>1,
			"|"=>0		
			);
	//print_r($infix_arr);
	//print_r($operators);
	$temp;
	//$infix_temp=array();

	foreach($infix_arr as $token){
		if(isOperator($token)){
			$prec=checkPrecedence($token);
			if(count($stack)==0&&$prec==3)
				echo "Invalid input string";
			else{
				if($prec==3){
					echo "here";
					while(count($stack)>0){
						$temp=$stack->pop();
						//echo "<br>Temp:".$temp;	
						if($temp!="(")
							array_push($postfix,$temp);
						else
							break;
					}
				}
				else
					$stack->push($token);
			}

		}
		else{
			array_push($postfix,$token);
		}
	}

	while(count($stack)>0){
		array_push($postfix,$stack->pop());
	}
	$stack->rewind();
	//echo "Stack contents"."<br>";
	while($stack->valid()){
		echo $stack->current(),PHP_EOL;
		$stack->next();
	}
	echo "<br>Postfix String<br>";
	print_r($postfix);

	function isOperator($char){
		return ($char=="("||$char=="*"||$char=="."||$char=="+"||$char==")"||$char=="|");
	}
	function checkPrecedence($char){
		global $operators;
		return  $operators[$char];
	}
	
	function postNFA($postfix){
		$graph=new Structures_Graph();
		$arr=array();
		for($i=0;$i<strlen($postfix);$i++){
			switch ($postfix[$i]) {
				case '':
					
					break;
				
				default:
					array_push($arr,$postfix[$i]);
					
			}
		}
	}


?>

<html>
	<head> 
		<title>Thompson NFA</title>
	</head>
	<body>
		<form action="inToPost.php" method="GET">
			<input type="text" placeholder="Enter string to create NFA" name="infix">
		</form>
	</body>
</html>