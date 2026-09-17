// Central place for all API calls to the Spring backend.
const BASE_URL = "http://localhost:8080/api";

// Plant Database / plant table -----------------------------------------------------------------------------------------------------
// GET all plants from the plant database/table
export async function getAllPlants() {
    const response = await fetch(`${BASE_URL}/plants`);
    if (!response.ok) {
        throw new Error("Failed to fetch plants");
    }
    return response.json();
}

// Users Plant Collection / collection_plant table -------------------------------------------------------------------------------------
// GET all plants in user's collection
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

// GET plants matching a search term
export async function searchPlants(name) {
    const response = await fetch(`${BASE_URL}/plants/search?name=${encodeURIComponent(name)}`);

    if (!response.ok) {
        throw new Error("Failed to search plants");
    }

    return response.json();
}

// POST add one plant to collection
export async function addToCollection(plantId, details) {
    const response = await fetch(`${BASE_URL}/collection-plants?plantId=${plantId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(details),
    });
    if (!response.ok) {
        throw new Error("Failed to add plant to collection.");
    }
    return response.json();
}

// PUT update notes for collection plant
export async function updateCollectionPlant(id, details) {
    const response = await fetch(`${BASE_URL}/collection-plants/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(details),
    });
    if (!response.ok) {
        const errorData = await response.json();
        throw errorData;
    }
    return response.json();
}

// DELETE one plant from collection
export async function deleteCollectionPlant(id) {
    const response = await fetch(`${BASE_URL}/collection-plants/${id}`, { method: "DELETE" });
    if (!response.ok) {
        throw new Error("Failed to delete plant.");
    }
}

// Progress Pictures / progress_picture table -----------------------------------------------------------------------------------------------------
// GET all progress pictures for a collection plant
export async function getProgressPictures(collectionPlantId) {
    const response = await fetch(
        `${BASE_URL}/progress-pictures?collectionPlantId=${collectionPlantId}`,
    );
    if (!response.ok) throw new Error("Failed to fetch progress pictures");
    return response.json();
}

// GET one progress picture by id
export async function getProgressPicture(id) {
    const response = await fetch(`${BASE_URL}/progress-pictures/${id}`);
    if (!response.ok) {
        throw new Error("Failed to fetch progress picture");
    }
    return response.json();
}

// POST one new progress picture
export async function uploadProgressPicture(collectionPlantId, file) {
    const formData = new FormData();
    formData.append("file", file); // "file" must match @RequestParam("file")

    const response = await fetch(
        `${BASE_URL}/progress-pictures/upload?collectionPlantId=${collectionPlantId}`,
        {
            method: "POST",
            body: formData, // NO headers object — let the browser set Content-Type
        },
    );

    if (!response.ok) throw new Error("Upload failed");
    return response.json();
}

// PUT update progress picture details
export async function updateProgressPicture(id, details) {
    const response = await fetch(`${BASE_URL}/progress-pictures/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(details),
    });
    if (!response.ok) {
        const errorData = await response.json();
        throw errorData;
    }
    return response.json();
}

// DELETE one progress picture
export async function deleteProgressPicture(id) {
    const response = await fetch(`${BASE_URL}/progress-pictures/${id}`, { method: "DELETE" });
    if (!response.ok) {
        throw new Error("Failed to delete progress picture.");
    }
}
