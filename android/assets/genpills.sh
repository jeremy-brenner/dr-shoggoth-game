for fn in "red" "yellow" "blue"; do
  cat pillbase.g3dj | sed "s/COLOR/${fn}/g" > ${fn}_pill.g3dj
done
