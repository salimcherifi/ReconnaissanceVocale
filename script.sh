for filename in *.wav; 
do 
    sox "$filename" temp.dat && (grep -v ";" temp.dat | awk '{ print $2}' > "$(basename "$filename" .wav).csv" ) && rm temp.dat; 
done