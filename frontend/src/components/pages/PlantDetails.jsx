import { useState } from "react";
import { useParams, useNavigate } from "react-router";
import { faTriangleExclamation } from "@fortawesome/free-solid-svg-icons";
import { altFromFileName } from "../../utils/altFromFileName";
import Button from "../ui/Button";
import PageTitle from "../ui/PageTitle";
import CareInstructions from "../features/CareInstructions";
import ProgressGallery from "../features/ProgressGallery";
import Loading from "../ui/Loading";
import SectionDivider from "../ui/SectionDivider";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";
import Modal from "../ui/Modal";

export default function PlantDetails({ collection, loading, removePlantFromCollection }) {
    const navigate = useNavigate();
    const { collectionId } = useParams();

    // Show the loading spinner until the collection data is available.
    if (loading) return <Loading />;

    // URL parameters are strings, so convert collectionId to a number for comparison.
    const plant = collection.find((plant) => plant.collectionId === Number(collectionId));

    // No matching plants state. -------------------------------------------------------------------------------
    if (!plant) {
        return (
            <main className="plant-not-found-in-collection-message">
                <h1>Plant not found!</h1>
                <p>The plant you are looking for is not in your collection.</p>
                <Button
                    innerText="Back to Collection"
                    onClick={() => navigate("/currentCollection")}
                />
            </main>
        );
    }

    // Matching plant state.  ------------------------------------------------------------------------------------
    return (
        <PlantDetailsContent plant={plant} removePlantFromCollection={removePlantFromCollection} />
    );
}

function PlantDetailsContent({ plant, removePlantFromCollection }) {
    const navigate = useNavigate();
    const [showConfirm, setShowConfirm] = useState(false);

    usePageTitleForBrowserTab("Plant Details");

    const [detailsData, setDetailsData] = useState({
        purchaseDate: plant.purchaseDate,
        storePurchasedFrom: plant.purchaseStore,
        cost: plant.cost,
        notes: plant.notes,
    });
    const [isEditing, setIsEditing] = useState(false);

    function handleChange(event) {
        const { name, value } = event.target;
        setDetailsData((prev) => ({ ...prev, [name]: value }));
    }

    function handleSave() {
        setIsEditing(false);
    }

    function handleRemove() {
        removePlantFromCollection(plant.collectionId);
        navigate("/currentCollection");
    }

    return (
        <main id="plantDetails">
            <Button innerText="Back" onClick={() => navigate(-1)} className="back-btn" />
            <img
                src={`${import.meta.env.BASE_URL}images/plants/${plant.image}`}
                alt={altFromFileName(plant.image)}
                className="details-page-image"
            />
            <PageTitle title={plant.name} />
            <h2>Plant Details</h2>
            <form>
                <label htmlFor="purchaseDate">Purchased Date:</label>
                <input
                    id="purchaseDate"
                    type="date"
                    name="purchaseDate"
                    value={detailsData.purchaseDate}
                    disabled={!isEditing}
                    onChange={handleChange}
                />
                <label htmlFor="storePurchasedFrom">Store Purchased From:</label>
                <input
                    id="storePurchasedFrom"
                    type="text"
                    name="storePurchasedFrom"
                    value={detailsData.storePurchasedFrom}
                    disabled={!isEditing}
                    onChange={handleChange}
                />
                <label htmlFor="cost">Cost:</label>
                <input
                    id="cost"
                    type="text"
                    name="cost"
                    value={detailsData.cost}
                    disabled={!isEditing}
                    onChange={handleChange}
                />
                <label htmlFor="notes">Notes:</label>
                <textarea
                    id="notes"
                    name="notes"
                    placeholder="Your notes here..."
                    rows="6"
                    maxLength="1000"
                    value={detailsData.notes}
                    disabled={!isEditing}
                    onChange={handleChange}
                />
                {isEditing ? (
                    <Button innerText="Save" onClick={handleSave} />
                ) : (
                    <Button innerText="Edit" onClick={() => setIsEditing(true)} />
                )}
            </form>
            <SectionDivider />
            <CareInstructions plant={plant} />
            <SectionDivider />
            <ProgressGallery plant={plant} />
            <SectionDivider />
            <div className="remove-btn-container">
                <Button
                    innerText="Remove Plant"
                    onClick={() => setShowConfirm(true)}
                    className="remove-btn"
                />
            </div>
            {/* Removing a plant is destructive, so the user must confirm the action in a modal.  */}
            <Modal
                role="dialog" // For screen readers.
                aria-modal="true" // This tells assistive technology that the dialog is modal.
                isOpen={showConfirm}
                onClose={() => setShowConfirm(false)}
                onConfirm={handleRemove}
                message={`Are you sure you want to remove the ${plant.name} from your collection?`}
                confirmText="Remove Plant"
                cancelText="Cancel"
                iconClassName="modal-yellow-warning-icon"
                confirmButtonClassName="remove-btn"
                icon={faTriangleExclamation}
            />
        </main>
    );
}
