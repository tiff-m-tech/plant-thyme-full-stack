import { useState, useEffect } from "react";
import { Routes, Route, Navigate } from "react-router";
import { getCollection, delectCollectionPlant } from "./services/api";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/pages/Home";
import Contact from "./components/pages/Contact";
import CurrentCollection from "./components/pages/CurrentCollection";
import AddPlant from "./components/pages/AddPlant";
import PlantDetails from "./components/pages/PlantDetails";
import LogIn from "./components/pages/LogIn";
import NotFound from "./components/pages/NotFound";
import ScrollToTop from "./components/layout/ScrollToTop";

function App() {
    const [collection, setCollection] = useState([]);
    const [loading, setLoading] = useState(true);
    // NOTE: setting as true so I stay logged in while building/testing
    const [isLoggedIn, setIsLoggedIn] = useState(true);

    useEffect(() => {
        async function loadCollection() {
            try {
                const data = await getCollection(); // fetch from backend
                setCollection(data);
            } catch (error) {
                console.error("Failed to load collection:", error);
            } finally {
                setLoading(false);
            }
        }
        loadCollection();
    }, []);

    function addPlantToCollection(plant) {
        const newCollectionId =
            collection.length > 0 ? Math.max(...collection.map((p) => p.collectionId)) + 1 : 1;
        const newEntry = {
            collectionId: newCollectionId,
            plantId: plant.id,
            name: plant.name,
            image: plant.image,
            purchaseDate: "",
            purchaseStore: "",
            cost: "",
            notes: "",
            progressPictures: [],
            careInstructions: [
                { light: plant.careInstructions[0].light },
                { water: plant.careInstructions[1].water },
                { fertilize: plant.careInstructions[2].fertilize },
            ],
        };
        setCollection((prev) => [...prev, newEntry]);
    }

    async function removePlantFromCollection(idToRemove) {
        try {
            await delectCollectionPlant(idToRemove);
            // Refresh from the database so state matches reality
            const updated = await getCollection();
            setCollection(updated);
        } catch (error) {
            console.error("Failed to delete plant:", error);
        }
    }

    // function removePlantFromCollection(idToRemove) {
    //     setCollection((prevCollection) =>
    //         prevCollection.filter((plant) => plant.collectionId !== idToRemove),
    //     );
    // }

    return (
        <>
            <ScrollToTop />
            <Header isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />
            {isLoggedIn ? (
                <Routes>
                    <Route path="/" element={<Navigate to="/home" replace />} />
                    <Route path="/home" element={<Home />} />
                    <Route path="/contact" element={<Contact />} />
                    <Route
                        path="/currentCollection"
                        element={<CurrentCollection collection={collection} loading={loading} />}
                    />
                    <Route
                        path="/currentCollection/add"
                        element={<AddPlant addPlantToCollection={addPlantToCollection} />}
                    />
                    <Route
                        path="/currentCollection/:collectionId"
                        element={
                            <PlantDetails
                                collection={collection}
                                loading={loading}
                                removePlantFromCollection={removePlantFromCollection}
                            />
                        }
                    />
                    <Route path="*" element={<NotFound />} />
                </Routes>
            ) : (
                <Routes>
                    <Route path="*" element={<LogIn setIsLoggedIn={setIsLoggedIn} />} />
                </Routes>
            )}

            <Footer />
        </>
    );
}

export default App;
