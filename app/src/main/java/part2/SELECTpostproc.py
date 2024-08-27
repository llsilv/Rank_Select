import matplotlib.pyplot as plt

with open('app/src/main/java/expResultsSELECT.txt', 'r') as file:
    data = file.read()

lines = data.strip().split('\n')
dataStructures = {'rsN': [], 'rsL': [], 'rsSE1': [], 'rsSE8': [], 'rsSE16': []}

for line in lines:
    parts = line.split()
    structure = parts[0]
    n = int(parts[3])
    fn = float(parts[4])
    dataStructures[structure].append((n, fn))

plt.figure(figsize=(10, 6))

for method, values in dataStructures.items():
    x_values, y_values = zip(*values)
    plt.plot(x_values, y_values, marker='o', linestyle='-', label=method)

plt.xlabel('n Values')
plt.ylabel('Mean value')
plt.title('n Values vs Mean value for Rank')
plt.legend()
plt.yscale('log')
plt.xscale('log') 
plt.savefig('SELECTpostproc.pdf', format='pdf')
plt.show()
