<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="person">
        <person>
            <xsl:for-each select="*[not(self::document)]">
                <xsl:attribute name="{name()}">
                    <xsl:value-of select="text()"/>
                </xsl:attribute>
            </xsl:for-each>
            <xsl:template match="person/document">
                <document>
                    <xsl:attribute name="series">
                        <xsl:value-of select="document/series"/>
                    </xsl:attribute>
                    <xsl:attribute name="number">
                        <xsl:value-of select="document/number"/>
                    </xsl:attribute>
                    <xsl:attribute name="type">
                        <xsl:value-of select="document/type"/>
                    </xsl:attribute>
                    <xsl:attribute name="issueDate">
                        <xsl:value-of select="document/issueDate"/>
                    </xsl:attribute>
                </document>
            </xsl:template>
        </person>
    </xsl:template>
</xsl:stylesheet>