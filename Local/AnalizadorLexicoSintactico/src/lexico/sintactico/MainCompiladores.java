package lexico.sintactico;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author JuanPablo
 */
public class MainCompiladores
{

	public final static int CREAR_ARCHIVOS = 1;
	public final static int EJECUATR_ARCHIVOS = 2;
	private static java.util.Scanner scanner;

	public static void main(String[] args)
	{
		scanner = new Scanner(System.in);
		int valor = 0;

		System.out.println("Digite 1 para crear los archivos o 2\n"
				+ "si desea ejecutar (para la opcion 2 los\n"
				+ "archivos deben haber sido generados\npreviamente");
		valor = scanner.nextInt();
		switch (valor)
		{

		case CREAR_ARCHIVOS:
		{
			System.out.println("\n*** Generando ***\n");
			String archLexico = "";
			String archSintactico = "";
			if (args.length > 0)
			{
				archLexico = args[0];
				archSintactico = args[1];
			} else
			{
				archLexico = "analizador_lexico_operaciones.flex";
				archSintactico = "analizador_sintactico_operaciones.cup";
			}
			String[] alexico =
			{ archLexico };
			String[] asintactico =
			{ "-parser", "AnalizadorSintactico", archSintactico };
			jflex.Main.main(alexico);
			try
			{
				java_cup.Main.main(asintactico);
			} catch (Exception ex)
			{
				Logger.getLogger(MainCompiladores.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			boolean mvAL = moverArch("AnalizadorLexico.java");
			boolean mvAS = moverArch("AnalizadorSintactico.java");
			boolean mvSym = moverArch("sym.java");
			if (mvAL && mvAS && mvSym)
			{
				System.out.println("Generado!");
				System.exit(0);
			}
		}
		case EJECUATR_ARCHIVOS:
		{
			String[] archivoPrueba =
			{ "test.txt" };
			AnalizadorSintactico.main(archivoPrueba);
			System.out
					.println("*********************************************\n");
			break;
		}
		default:
		{
			System.out.println("Opcion no valida ADIOS =(!!!!");
		}
		}

	}

	public static boolean moverArch(String archNombre)
	{
		boolean efectuado = false;
		File arch = new File(archNombre);
		if (arch.exists())
		{
			Path currentRelativePath = Paths.get("");
			String nuevoDir = currentRelativePath.toAbsolutePath().toString()
					+ File.separator + "src" + File.separator
					+ "lexico/sintactico" + File.separator + arch.getName();
			File archViejo = new File(nuevoDir);
			archViejo.delete();
			if (arch.renameTo(new File(nuevoDir)))
			{
				efectuado = true;
			}
		}
		return efectuado;
	}
}