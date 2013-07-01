<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" encoding="utf-8" indent="yes" doctype-public="-//W3C//DTD SVG 1.0//EN' 'http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd'"/>
    <!--Identity transform copies all items by default -->
    <xsl:template match="@* | node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

    <!--Match on element, wrap in a comment and construct text representing XML structure by applying templates in "comment" mode -->
    <xsl:template match="//*[contains(text(),'Visual Paradigm for UML Community Edition')]">
        <xsl:comment>
            <xsl:apply-templates select="self::*" mode="comment" />
        </xsl:comment>
    </xsl:template>

    <xsl:template match="*" mode="comment">
        <xsl:value-of select="'&lt;'"/>
            <xsl:value-of select="name()"/>
        <xsl:value-of select="'&gt;'"/>
            <xsl:apply-templates select="@*|node()" mode="comment" />
        <xsl:value-of select="'&lt;/'"/>
            <xsl:value-of select="name()"/>
        <xsl:value-of select="'&gt;'"/>
    </xsl:template>

    <xsl:template match="text()" mode="comment">
        <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="@*" mode="comment">
        <xsl:value-of select="name()"/>
        <xsl:text>="</xsl:text>
        <xsl:value-of select="."/>
        <xsl:text>" </xsl:text>
    </xsl:template>

</xsl:stylesheet>
