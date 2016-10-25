/* Dette er en test paa diverse *odde* kommentarer.
   Det er faktisk lovlig.

/* Dette er en del av samme kommentar,
   men naa er det slutt. */

program Kommentar;
var type: integer;
begin 
   type := -{NB! negativt}1;
   write('t', /* som er kortform av "type" */ '=', type,
	 {og saa linjeskift til slutt:} EoL)
end {Hovedprogrammet}. 

{ Det er lov aa ha kommentarer _etter_ programmet ogsaa,
  baade /*...*/-kommentarer og de med kroellparenteser.
}
