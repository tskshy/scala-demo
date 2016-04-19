lazy val common_settings = Seq(
	organization	:= "com.demo",
	version		:= "0.0.0",
	scalaVersion	:= "2.11.7"
)

lazy val hw = (project in file("hw")).settings(common_settings: _*).settings(
	name		:= "hello"
)
