<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
                xmlns:m0="http://services.samples/xsd"
                exclude-result-prefixes="m0 fn">
<xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

<xsl:template match="/">

    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">

        <soapenv:Body>
           <xsl:apply-templates select="//m0:skcotSyub"/>
        </soapenv:Body>

    </soapenv:Envelope>

</xsl:template>

<xsl:template match="m0:skcotSyub">
    <m:buyStocks xmlns:m="http://services.samples/xsd">
        <xsl:for-each select="redro">
            <order>
                <symbol>
                    <xsl:value-of select="lobmys"/>
                </symbol>
                <buyerID>
                    <xsl:value-of select="DIreyub"/>
                </buyerID>
                <price>
                    <xsl:value-of select="ecirp"/>
                </price>
                <volume>
                    <xsl:value-of select="emulov"/>
                </volume>
            </order>
        </xsl:for-each>
    </m:buyStocks>
</xsl:template>

</xsl:stylesheet>
