/*===========================================================================
*
*                            PUBLIC DOMAIN NOTICE
*               National Center for Biotechnology Information
*
*  This software/database is a "United States Government Work" under the
*  terms of the United States Copyright Act.  It was written as part of
*  the author's official duties as a United States Government employee and
*  thus cannot be copyrighted.  This software/database is freely available
*  to the public for use. The National Library of Medicine and the U.S.
*  Government have not placed any restriction on its use or reproduction.
*
*  Although all reasonable efforts have been taken to ensure the accuracy
*  and reliability of the software and data, the NLM and the U.S.
*  Government do not and cannot warrant the performance or results that
*  may be obtained by using this software or data. The NLM and the U.S.
*  Government disclaim all warranties, express or implied, including
*  warranties of performance, merchantability or fitness for any particular
*  purpose.
*
*  Please cite the author in any work or product based on this material.
*
* ===========================================================================
*
*/

package gov.nih.nlm.ncbi.ngs;

import ngs.ErrorMsg;
import ngs.ReadCollection;
import ngs.itf.ReadCollectionItf;
import ngs.ReferenceSequence;
import ngs.itf.ReferenceSequenceItf;


/*==========================================================================
 * Manager
 *  NGS library manager
 */
class Manager
{

    void setAppVersionString ( String app_version )
    {
        if ( invalid != null ) {
            throw invalid;
        }

        SetAppVersionString ( app_version );
    }

    ReadCollection openReadCollection ( String spec )
        throws ErrorMsg
    {
        if ( invalid != null ) {
            throw invalid;
        }

        long ref = this . OpenReadCollection ( spec );
        try
        {
            return new ReadCollectionItf ( ref );
        }
        catch ( Exception x )
        {
            this . release ( ref );
            throw new ErrorMsg ( x . toString () );
        }
    }

    ReferenceSequence openReferenceSequence ( String spec )
        throws ErrorMsg
    {
        if ( invalid != null ) {
            throw invalid;
        }

        long ref = this . OpenReferenceSequence ( spec );
        try
        {
            return new ReferenceSequenceItf ( ref );
        }
        catch ( Exception x )
        {
            this . release ( ref );
            throw new ErrorMsg ( x . toString () );
        }
    }

    Manager ()
    {
        try
        {
            /* Load the DLL for JNI (download it?).

               To do just a plain call to System.LoadLibrary(libname)
                set LibManager.JUST_DO_REGULAR_JAVA_SYSTEM_LOAD_LIBRARY to true
                or set vdb.System.loadLibrary java system property. */

            String ngs_sdk  = "ngs-sdk";
            String ncbi_vdb = "ncbi-vdb";

            LibManager m = new LibManager( new String[] { ngs_sdk, ncbi_vdb } );
            if ( ! m . loadLibrary ( ngs_sdk ) )
                throw new ExceptionInInitializerError
                    ( "Cannot load " + ngs_sdk + " library" );
            if ( ! m . loadLibrary ( ncbi_vdb ) )
                throw new ExceptionInInitializerError
                    ( "Cannot load " + ncbi_vdb + " library" );

            // try to initialize the NCBI library
            String err = Initialize ();
            if ( err != null )
                throw new ExceptionInInitializerError ( err );

            // install shutdown hook for library cleanup
            Runtime . getRuntime () . addShutdownHook
            (
                new Thread ()
                {
                    public void run ()
                    {
                        Shutdown ();
                    }
                }
            );
        }
        catch ( ExceptionInInitializerError x )
        {
            invalid = x;
        }
        catch ( Throwable x )
        {
            invalid = new ExceptionInInitializerError ( x );
        }
    }

    /** ncbi-vdb version */
    static String getPackageVersion()
    {
        try
        {
            return Version ();
        }
        catch ( Throwable e )
        {
            return "";
        }
    }

    boolean isValid () { return invalid == null; }

    private native static String Version ();
    private native static String Initialize ();
    private native static void Shutdown ();
    private native static void SetAppVersionString ( String app_version );
    private native static long OpenReadCollection ( String spec )
        throws ErrorMsg;
    private native static long OpenReferenceSequence ( String spec )
        throws ErrorMsg;
    private native static void release ( long ref );

    ExceptionInInitializerError invalid;
}
