<!--
 *================================================================================
 *
 * Add debugger information to an info.plist file
 *
 * Author : Mike Butler (mjb)
 *
 *================================================================================
  -->
  
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="xml" indent="yes" />
	
	<!-- Simple way to recognise the first dict node -->
	<xsl:variable name="firstDictID" select="generate-id(/plist/dict[1])"/>
	
	<!-- The actual VMOptions command to be added to the info.plist -->
	<xsl:variable name="debugCommand" >-Xdebug -Xnoagent 
		-Xrunjdwp:transport=dt_socket,server=n,address=8000</xsl:variable>
	
<!--/**
	 * Root Node.  The XSL version of main
	 * ( Although it's not strictly necessary in this instance )
	 */ -->
	 <xsl:template match="/">
		<xsl:apply-templates />
	 </xsl:template>

	 
<!--/**
	 *  Catch the first instance of <dict> and if there is no VMOptions entry then add one
	 */ -->
	 <xsl:template match="dict">
	 
	 	<xsl:copy>
			<xsl:copy-of select="@*" />

			<!-- if this is the first <dict> node and there are is no VMOptions entry then add one -->
			<xsl:if test="generate-id() = $firstDictID and false() = (//key='VMOptions')">
				<key>Java</key>
				<dict>
					<key>VMOptions</key>
					<string><xsl:value-of select="$debugCommand"/></string>
				</dict>
			</xsl:if>
			
		 	<!-- continue as normal-->
			<xsl:apply-templates/>
			
		</xsl:copy>
	 </xsl:template>


	
<!--/**
	 * Catch the <string> value of a VMOption <key> and add the appropriate options if the debug param isn't already set.
	 *
	 */ -->
	 <xsl:template match="string">
	 	<xsl:copy>
			<xsl:copy-of select="@*" />
			<xsl:value-of select="."/>

			<!-- only add the debug command if the previous node was a VMOptions key, and the existing VMOptions do not include the debug parameter -->
			<xsl:if test="preceding-sibling::*[1] = 'VMOptions' and false = contains(., '-Xdebug' )">
				<xsl:text> </xsl:text><xsl:value-of select="$debugCommand"/>
			</xsl:if>

		</xsl:copy>	 	
	 </xsl:template>

	 
<!--/**
	 * Copy everything
	 */ -->
     <xsl:template match="node()">
        <xsl:copy>
        	<xsl:copy-of select="@*" />
            <xsl:apply-templates />
        </xsl:copy>
	 </xsl:template>


</xsl:stylesheet>