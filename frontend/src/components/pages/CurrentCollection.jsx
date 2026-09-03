import { useState } from "react";
import { useNavigate } from "react-router";
import CollectionCard from "../cards/CollectionCard";
import Button from "../ui/Button";
import PageTitle from "../ui/PageTitle";
import SearchBar from "../ui/SearchBar";
import Loading from "../ui/Loading";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";

export default function CurrentCollection({ collection, loading }) {
    const currentCollectionImagePath = `${import.meta.env.BASE_URL}images/brand/current-collection.webp`;
    const plantCount = collection.length;
    const navigate = useNavigate();
    const [searchValue, setSearchValue] = useState("");
    // Filter collection by search term or show all if no term.
    const displayedPlants = searchValue.trim()
        ? collection.filter((plant) =>
              plant.name.toLowerCase().includes(searchValue.toLowerCase().trim()),
          )
        : collection;

    function handleChange(event) {
        setSearchValue(event.target.value);
    }

    usePageTitleForBrowserTab("Current Collection");

    // Show the loading spinner while collection data is loading. ---------------------------------------------------------
    if (loading) {
        return (
            <main id="currentCollection">
                <img src={currentCollectionImagePath} alt="" className="large-page-image" />
                <PageTitle title="My Leafy Collection" />
                <div className="collection-page-controls-container">
                    <Loading />
                </div>
            </main>
        );
    }

    return (
        <main id="currentCollection">
            <img src={currentCollectionImagePath} alt="" className="large-page-image" />
            <PageTitle title="My Leafy Collection" />
            {plantCount === 0 ? (
                <div>
                    {/* Show a message when the collection is empty. ------------------------------------------------------- */}
                    <p className="empty-collection-text">
                        You have not added any plants to your collection yet. Let's fill this list
                        up with leafy friends! 🪴🥰
                    </p>
                    <Button
                        innerText="Add Plant"
                        onClick={() => navigate("/currentCollection/add")}
                    />
                </div>
            ) : (
                <>
                    {/* Show a message when no plants match the search. ------------------------------------------------------*/}
                    <div className="collection-page-controls-container">
                        <h2 className="plant-count">Plant Count: {plantCount}</h2>
                        <Button
                            innerText="Add Plant"
                            onClick={() => navigate("/currentCollection/add")}
                        />
                        <SearchBar
                            value={searchValue}
                            onChange={handleChange}
                            placeholder="Search your leafy friends..."
                            showButton={false}
                        />
                    </div>
                    {displayedPlants.length === 0 ? (
                        <p>No plants match your search.</p>
                    ) : (
                        <div className="collection-cards-container">
                            {/* Show matching plants in alphabetical order + copy array so displayedPlants is not modified. -------------------*/}
                            {[...displayedPlants]
                                .sort((a, b) => a.name.localeCompare(b.name))
                                .map((plant) => (
                                    <CollectionCard
                                        key={plant.collectionId}
                                        collectionId={plant.collectionId}
                                        imgPath={plant.image}
                                        name={plant.name}
                                        light={plant.careInstructions[0].light}
                                        water={plant.careInstructions[1].water}
                                        fertilize={plant.careInstructions[2].fertilize}
                                    />
                                ))}
                        </div>
                    )}
                </>
            )}
        </main>
    );
}
