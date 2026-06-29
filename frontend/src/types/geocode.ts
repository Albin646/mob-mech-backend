const API_KEY = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

export async function getAddress(latitude: number, longitude: number) {
  const url =
    `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=${API_KEY}`;

  console.log(url);

  const response = await fetch(url);

  const data = await response.json();

  console.log("GEOCODE RESPONSE", data);

  if (data.status !== "OK") {
    return "Unknown location";
  }

  return data.results[0].formatted_address;
}