lazy val common_settings = Seq(
	organization	:= "com.demo",
	version		:= "0.0.0",
	scalaVersion	:= "2.11.7"
)

lazy val root = (project in file(".")).settings(common_settings: _*).settings(
	name		:= "root"
)

//hello world demo
lazy val hw = (project in file("hw")).settings(common_settings: _*).settings(
	name		:= "hello"
)

//dsl demo
lazy val dsl = (project in file("dsl")).settings(common_settings: _*).settings(
	name		:= "dsl"
)

//trait demo
lazy val tr = (project in file("trait")).settings(common_settings: _*).settings(
	name 		:= "trait"
)
