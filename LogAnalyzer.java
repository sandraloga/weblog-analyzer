/**
 * Read web server data and analyse
 * hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    /**
     * nuevo constructor tendr� un par�metro consistente en el nombre del archivo de log a analiza
     */
    public LogAnalyzer(String archivoLog)
    {
        hourCounts = new int[24];
        reader = new LogfileReader(archivoLog);
    }

    /**
     * Analyze the hourly access data from the log file.
     */

    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    /**
     * Crea en la clase LogAnalyzer un m�todo llamado numberOfAccesses
     * que se pueda ejecutar despu�s del m�todo analyzeHourlyData y 
     * que devuelva el n�mero total de accesos al servidor web registrados en el archivo de log.
     */
    public int numberOffAccesses()
    {
        int contadorAccesos = 0;
        for (int index=0 ; index < hourCounts.length ; index++)
        {
            contadorAccesos = contadorAccesos +  hourCounts[index];  
        }
        return contadorAccesos;
    }

    /**
     *  M�todo que devuelve en qu� hora el servidor
     *  tuvo que responder a m�s peticiones. Si hay empate devuelve la �ltima de las horas. 
     *  Si no ha habido accesos informa del hecho por pantalla y devuelve -1.
     */
    public int busiestHour()
    {
        int horaMasPeticiones = 0;
        int comparador = 0;
        for (int index=0 ; index < hourCounts.length ; index++)
        {
            if (hourCounts[index] >= comparador)
            {
                comparador =hourCounts[index];
                horaMasPeticiones = index;

            }

        }
        if (comparador == 0)
        {
            System.out.println("No ha habido accesos");  
            horaMasPeticiones = -1;  
        }
        return horaMasPeticiones;
    }

    /**
     * Metodo que devuelve la hora a la que el servidor estuvo menos sobrecargado. Si hay empate devuelve 
     * la �ltima de las horas.  Si no ha habido accesos informa del hecho por pantalla y devuelve -1.
     */
    public int quietestHour()
    {
        int horaMenosPeticiones = 0;
        int comparador =  hourCounts[busiestHour()];
        for (int index=0 ; index < hourCounts.length ; index++)
        {
            if (hourCounts[index] <= comparador)
            {
                comparador = hourCounts[index];
                horaMenosPeticiones = index;

            }

        }
        if (comparador == 0)
        {
            System.out.println("No ha habido accesos");
            horaMenosPeticiones = -1;  
        }
        return horaMenosPeticiones;

    }

    /**
     * M�todo que muesta por pantalla el per�odo de dos horas consecutivas con m�s carga del d�a y devuelva un entero con la primera hora de dicho periodo.
     * Si hay empate devuelve el �ltimo per�odo. Si no ha habido accesos informa del hecho por pantalla y devuelve -1.
     */
    public int mayorRangoVisitas()
    {
        int rangoMayor = 0;
        int comparador=0;
        int rangoExtra=0;
        int indexExtra=0;
        for (int index=1 ; index < hourCounts.length ; index=index+2)
        {
             if ( index == hourCounts.length - 1)
            {
                rangoExtra = hourCounts[index]+ hourCounts[0] ;
                indexExtra = index;
            }
            else
           {
                if ((hourCounts[index] +hourCounts[index+1]) >= comparador)
                {

                comparador = hourCounts[index] +hourCounts[index+1];
                rangoMayor = index;
                }
           }
            
           
             if (rangoExtra >= comparador)
            {
                comparador = rangoExtra;   
                rangoMayor = indexExtra;
            }
           

            if (comparador == 0)
            {
                System.out.println("No ha habido accesos");
                rangoMayor = -1;  
            }
           
        }
         return rangoMayor;
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        int hour = 0;
        while(hour < hourCounts.length)
        {
            System.out.println(hour + ": " + hourCounts[hour]);
            hour++;
        }

    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
