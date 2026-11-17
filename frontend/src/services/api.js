// Central place for all API calls to the Spring backend.
const BASE_URL = "http://localhost:8080/api";

// Auth token helpers ---------------------------------------------------------------------------------------------------------
// The JWT is saved in localStorage so the user stays logged in after a page refresh.
const TOKEN_KEY = "plantThymeToken";

export function saveToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
}

export function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

export function clearToken() {
    localStorage.removeItem(TOKEN_KEY);
}

// Adds "Authorization: Bearer <token>" to a request, if there is a token
function authHeader() {
    const token = getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
}

// Auth / users table --------------------------------------------------------------------------------------------------------
// POST log in — returns { token, user }
export async function loginUser(username, password) {
    const response = await fetch(`${BASE_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
    });
    if (!response.ok) {
        const errorData = await response.json();
        throw errorData;
    }
    return response.json();
}

// POST create a new account — returns the new user
export async function registerUser(username, email, password) {
    const response = await fetch(`${BASE_URL}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, email, password }),
    });
    if (!response.ok) {
        const errorData = await response.json();
        throw errorData;
    }
    return response.json();
}

// GET the logged-in user — used to check if a saved token is still valid
export async function getCurrentUser() {
    const response = await fetch(`${BASE_URL}/auth/me`, { headers: authHeader() });
    if (!response.ok) {
        throw new Error("Not logged in");
    }
    return response.json();
}

// Plant Database / plant table -----------------------------------------------------------------------------------------------------
// GET all plants from the plant database/table
export async function getAllPlants() {
    const response = await fetch(`${BASE_URL}/plants`, { headers: authHeader() });
    if (!response.ok) {
        throw new Error("Failed to fetch plants");
    }
    return response.json();
}

// Users Plant Collection / collection_plant table -------------------------------------------------------------------------------------
// GET all plants in user's collection
export async function getCollection() {
    const response = await fetch(`${BASE_URL}/collection-plants`, { headers: authHeader() });
    if (!response.ok) {
        throw new Error("Failed to fetch collection");
    }
    return response.json();
}

// GET one collection plant by id
export async function getCollectionPlant(id) {
    const response = await fetch(`${BASE_URL}/collection-plants/${id}`, { headers: authHeader() });
    if (!response.ok) {
        throw new Error("Failed to fetch collection plant");
    }
    return response.json();
}

// GET plants matching a search term
export async function searchPlants(name) {
    const response = await fetch(`${BASE_URL}/plants/search?name=${encodeURIComponent(name)}`, {
        headers: authHeader(),
    });

    if (!response.ok) {
        throw new Error("Failed to search plants");
    }

    return response.json();
}

// POST add one plant to collection
export async function addToCollection(plantId, details) {
    const response = await fetch(`${BASE_URL}/collection-plants?plantId=${plantId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json", ...authHeader() },
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
        headers: { "Content-Type": "application/json", ...authHeader() },
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
    const response = await fetch(`${BASE_URL}/collection-plants/${id}`, {
        method: "DELETE",
        headers: authHeader(),
    });
    if (!response.ok) {
        throw new Error("Failed to delete plant.");
    }
}

// Progress Pictures / progress_picture table -----------------------------------------------------------------------------------------------------
// GET all progress pictures for a collection plant
export async function getProgressPictures(collectionPlantId) {
    const response = await fetch(
        `${BASE_URL}/progress-pictures?collectionPlantId=${collectionPlantId}`,
        { headers: authHeader() },
    );
    if (!response.ok) throw new Error("Failed to fetch progress pictures");
    return response.json();
}

// GET one progress picture by id
export async function getProgressPicture(id) {
    const response = await fetch(`${BASE_URL}/progress-pictures/${id}`, { headers: authHeader() });
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
            headers: authHeader(),
            body: formData,
        },
    );

    if (!response.ok) throw new Error("Upload failed");
    return response.json();
}

// PUT update progress picture details
export async function updateProgressPicture(id, details) {
    const response = await fetch(`${BASE_URL}/progress-pictures/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json", ...authHeader() },
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
    const response = await fetch(`${BASE_URL}/progress-pictures/${id}`, {
        method: "DELETE",
        headers: authHeader(), // CHANGED
    });
    if (!response.ok) {
        throw new Error("Failed to delete progress picture.");
    }
}
