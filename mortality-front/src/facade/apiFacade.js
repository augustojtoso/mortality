import axios from 'axios';

const fetchMortalityRates = async (year) => {
  try {
    const response = await axios.get(`/mortalityrates/years/${year}/countries`);
    return response.data.mortalityRates;
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.warn('Data not found for the year:', year);
      return {};
    } else {
      console.error('Error fetching data:', error);
      throw error;
    }
  }
};

const saveMortalityRate = async (year, countryCode, data) => {
    try {
        const response = await axios.post(`/mortalityrates/years/${year}/countries/${countryCode}`, data);
        return response.data.mortalityRates;
    } catch (error) {
        console.error('Error saving data:', error);
        throw error;
    }
};

export { fetchMortalityRates, saveMortalityRate };