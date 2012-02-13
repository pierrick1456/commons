#!/bin/bash
mkdir ./tests/temp
mv ./scenario/wso2esb-3.0.0_xslt/message* ./tests/temp/
cp -f ./tests/temp/message256b.xml ./scenario/wso2esb-3.0.0_xslt/
mv ./conf/config.xml ./tests/temp
cp -f ./tests/config_256b.xml ./conf/config.xml
./Test.pl -a
mv ./tests/temp/config.xml ./conf
mv ./tests/temp/* ./scenario/wso2esb-3.0.0_xslt/
rm -rf tests/temp
