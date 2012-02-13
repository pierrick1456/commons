#!/bin/bash
mkdir ./tests/temp
mv ./scenario/wso2esb-3.0.0_pass/message* ./tests/temp/
cp -f ./tests/temp/message1mb.xml ./scenario/wso2esb-3.0.0_pass/
mv ./conf/config.xml ./tests/temp
cp -f ./tests/wso2esb-3.0.0_pass/config_1mb.xml ./conf/config.xml
./Test.pl
mv ./tests/temp/config.xml ./conf
mv ./tests/temp/* ./scenario/wso2esb-3.0.0_pass/
rm -rf tests/temp
