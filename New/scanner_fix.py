import re


package_name = 'grammar'

content = None
with open('./src/%s/ScannerConstants.java' % package_name, 'r') as file:
	content = file.readlines();

	
new_content = list()
scanner_table = list()
saw_scanner_def = False
finished_seeing_table = False

for line in content:
	if saw_scanner_def and not finished_seeing_table:
		if line.endswith(';'):
			finished_seeing_table = True
		else:
			scanner_table.add(re.sub('[\{\}\,]', '', line))
	else:
		if 'SCANNER_TABLE' in line:
			saw_scanner_def = True
			new_content.add('    int[][] SCANNER_TABLE = new Importador().getTabela();')
		else:
			new_content.add(line)
			
with open('./src/%s/ScannerConstants.java' % package_name, 'w') as file:
	file.writelines(new_content)
	
with open('./arquivo.txt', 'w') as file:
	file.writelines(scanner_table)