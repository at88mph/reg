/*
************************************************************************
*******************  CANADIAN ASTRONOMY DATA CENTRE  *******************
**************  CENTRE CANADIEN DE DONNÉES ASTRONOMIQUES  **************
*
*  (c) 2024.                            (c) 2024.
*  Government of Canada                 Gouvernement du Canada
*  National Research Council            Conseil national de recherches
*  Ottawa, Canada, K1A 0R6              Ottawa, Canada, K1A 0R6
*  All rights reserved                  Tous droits réservés
*
*  NRC disclaims any warranties,        Le CNRC dénie toute garantie
*  expressed, implied, or               énoncée, implicite ou légale,
*  statutory, of any kind with          de quelque nature que ce
*  respect to the software,             soit, concernant le logiciel,
*  including without limitation         y compris sans restriction
*  any warranty of merchantability      toute garantie de valeur
*  or fitness for a particular          marchande ou de pertinence
*  purpose. NRC shall not be            pour un usage particulier.
*  liable in any event for any          Le CNRC ne pourra en aucun cas
*  damages, whether direct or           être tenu responsable de tout
*  indirect, special or general,        dommage, direct ou indirect,
*  consequential or incidental,         particulier ou général,
*  arising from the use of the          accessoire ou fortuit, résultant
*  software.  Neither the name          de l'utilisation du logiciel. Ni
*  of the National Research             le nom du Conseil National de
*  Council of Canada nor the            Recherches du Canada ni les noms
*  names of its contributors may        de ses  participants ne peuvent
*  be used to endorse or promote        être utilisés pour approuver ou
*  products derived from this           promouvoir les produits dérivés
*  software without specific prior      de ce logiciel sans autorisation
*  written permission.                  préalable et particulière
*                                       par écrit.
*
*  This file is part of the             Ce fichier fait partie du projet
*  OpenCADC project.                    OpenCADC.
*
*  OpenCADC is free software:           OpenCADC est un logiciel libre ;
*  you can redistribute it and/or       vous pouvez le redistribuer ou le
*  modify it under the terms of         modifier suivant les termes de
*  the GNU Affero General Public        la “GNU Affero General Public
*  License as published by the          License” telle que publiée
*  Free Software Foundation,            par la Free Software Foundation
*  either version 3 of the              : soit la version 3 de cette
*  License, or (at your option)         licence, soit (à votre gré)
*  any later version.                   toute version ultérieure.
*
*  OpenCADC is distributed in the       OpenCADC est distribué
*  hope that it will be useful,         dans l’espoir qu’il vous
*  but WITHOUT ANY WARRANTY;            sera utile, mais SANS AUCUNE
*  without even the implied             GARANTIE : sans même la garantie
*  warranty of MERCHANTABILITY          implicite de COMMERCIALISABILITÉ
*  or FITNESS FOR A PARTICULAR          ni d’ADÉQUATION À UN OBJECTIF
*  PURPOSE.  See the GNU Affero         PARTICULIER. Consultez la Licence
*  General Public License for           Générale Publique GNU Affero
*  more details.                        pour plus de détails.
*
*  You should have received             Vous devriez avoir reçu une
*  a copy of the GNU Affero             copie de la Licence Générale
*  General Public License along         Publique GNU Affero avec
*  with OpenCADC.  If not, see          OpenCADC ; si ce n’est
*  <http://www.gnu.org/licenses/>.      pas le cas, consultez :
*                                       <http://www.gnu.org/licenses/>.
*
*  $Revision: 5 $
*
************************************************************************
 */

package org.opencadc.reg;

import ca.nrc.cadc.auth.AuthenticationUtil;
import ca.nrc.cadc.reg.Capabilities;
import ca.nrc.cadc.reg.Capability;
import ca.nrc.cadc.reg.Interface;
import ca.nrc.cadc.reg.Standards;
import ca.nrc.cadc.util.Log4jInit;
import ca.nrc.cadc.vosi.CapabilitiesTest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.security.auth.Subject;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;

/**
 *
 * @author pdowler
 */
public class VosiCapabilitiesTest extends CapabilitiesTest {

    private static final Logger log = Logger.getLogger(VosiCapabilitiesTest.class);

    public static final String CAP_URL_PROP = "org.opencadc.reg.capabilitiesURL";
    public static URL CAP_URL;
    
    static {
        Log4jInit.setLevel("org.opencadc.reg", Level.INFO);
        String surl = System.getProperty(CAP_URL_PROP);
        if (surl == null) {
            String msg = "TEST CONFIG: set java system property '" + CAP_URL_PROP + "'";
            log.error(msg);
            throw new RuntimeException(msg);
        }
        try {
            CAP_URL = new URL(surl);
        } catch (MalformedURLException ex) {
            String msg = "TEST CONFIG: " + CAP_URL_PROP + "=" + surl 
                    + " but the URL is invalid: " + ex.toString();
            throw new RuntimeException(msg, ex);
        }
    }
    
    public static Capabilities getCapabilities() throws IOException, URISyntaxException {
        Subject anon = AuthenticationUtil.getAnonSubject();
        return CapabilitiesTest.getCapabilitiesFromServer(anon, CAP_URL);
    }

    public VosiCapabilitiesTest() {
        super(CAP_URL);
    }

    @Override
    protected void validateContent(Capabilities caps)
            throws Exception {
        super.validateContent(caps);
        
        for (Capability c : caps.getCapabilities()) {
            log.debug("capability: " + c.getStandardID());
            for (Interface i : c.getInterfaces()) {
                for (URI sm : i.getSecurityMethods()) {
                    log.debug("\tinterface: " + i.getType() + " " + sm + " " + i.getAccessURL().getURL());
                }
            }
        }
        
        // repo
        Capability reg = caps.findCapability(Standards.REGISTRY_10);
        Assert.assertNotNull(reg);
        
        // interfaces that should be present
        Assert.assertNotNull("anon oai", reg.findInterface(Standards.SECURITY_METHOD_ANON, Standards.INTERFACE_REG_OAI));
    }

    @Override
    public void testTokenAuth() throws Exception {
        // reg is anon only
        //super.testTokenAuth();
    }
}
