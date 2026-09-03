// Central place for all API calls to the Spring backend.
const BASE_URL = "http://localhost:8080/api";

// GET all plants from the master plant database
export async function getAllPlants() {
    const response = await fetch(`${BASE_URL}/plants`);
    if (!response.ok) {
        throw new Error("Failed to fetch plants");
    }
    return response.json();
}

// GET all plants in the user's collection
export async function getCollection() {
    const response = await fetch(`${BASE_URL}/collection-plants`);
    if (!response.ok) {
        throw new Error("Failed to fetch collection");
    }
    return response.json();
}

// GET one collection plant by id
export async function getCollectionPlant(id) {
    const response = await fetch(`${BASE_URL}/collection-plants/${id}`);
    if (!response.ok) {
        throw new Error("Failed to fetch collection plant");
    }
    return response.json();
}

// GET progress pictures for a collection plant
export async function getProgressPictures(collectionPlantId) {
    const response = await fetch(`${BASE_URL}/progress-pictures`);
    if (!response.ok) throw new Error("Failed to fetch progress pictures");
    return response.json();
}
