import { useState } from "react";
import { useParams, useNavigate } from "react-router";
import { faTriangleExclamation, faTrashCan } from "@fortawesome/free-solid-svg-icons";
import { altFromFileName } from "../../utils/altFromFileName";
import { updateCollectionPlant } from "../../services/api";
import Button from "../ui/Button";
import PageTitle from "../ui/PageTitle";
import CareInstructions from "../features/CareInstructions";
import ProgressGallery from "../features/ProgressGallery";
import Loading from "../ui/Loading";
import SectionDivider from "../ui/SectionDivider";
import { usePageTitleForBrowserTab } from "../../hooks/usePageTitleForBrowserTab";
import Modal from "../ui/Modal";

export default function PlantDetails({
    collection,
    loading,
    removePlantFromCollection,
    refreshCollection,
}) {
    const navigate = useNavigate();
    const { collectionId } = useParams();

    // Show the loading spinner until the collection data is available.
    if (loading) return <Loading />;

    // URL parameters are strings, so convert collectionId to a number for comparison.
    const collectionPlant = collection.find(
        (collectionPlant) => collectionPlant.id === Number(collectionId),
    );

    // No matching plants state. -------------------------------------------------------------------------------
    if (!collectionPlant) {
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
        <PlantDetailsContent
            collectionPlant={collectionPlant}
            removePlantFromCollection={removePlantFromCollection}
            refreshCollection={refreshCollection}
        />
    );
}

function PlantDetailsContent({ collectionPlant, removePlantFromCollection, refreshCollection }) {
    const navigate = useNavigate();
    const [showConfirm, setShowConfirm] = useState(false);

    usePageTitleForBrowserTab("Plant Details");

    const [detailsData, setDetailsData] = useState({
        purchaseDate: collectionPlant.purchaseDate ?? "",
        storePurchasedFrom: collectionPlant.purchaseStore ?? "",
        cost: collectionPlant.cost ?? "",
        nickname: collectionPlant.nickname ?? "",
        location: collectionPlant.location ?? "",
        notes: collectionPlant.notes ?? "",
        showNickname: collectionPlant.showNickname ?? false,
        showLocation: collectionPlant.showLocation ?? false,
    });
    const [isEditing, setIsEditing] = useState(false);

    // handleChange updates whichever field changed, keyed by the input's name.
    // - Text/date/textarea inputs store their value (a string) from event.target.value.
    // - Checkboxes store true/false from event.target.checked

    function handleChange(event) {
        const { name, value, type, checked } = event.target;
        setDetailsData((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    }

    async function handleSave() {
        try {
            const updatedDetails = {
                purchaseDate: detailsData.purchaseDate === "" ? null : detailsData.purchaseDate,
                purchaseStore:
                    detailsData.storePurchasedFrom === "" ? null : detailsData.storePurchasedFrom,
                cost: detailsData.cost === "" ? null : Number(detailsData.cost),
                nickname: detailsData.nickname === "" ? null : detailsData.nickname,
                location: detailsData.location === "" ? null : detailsData.location,
                notes: detailsData.notes === "" ? null : detailsData.notes,
                showNickname: detailsData.showNickname,
                showLocation: detailsData.showLocation,
            };
            await updateCollectionPlant(collectionPlant.id, updatedDetails);
            await refreshCollection();
            setIsEditing(false);
        } catch (error) {
            console.error("Failed to update plant:", error);
        }
    }

    function handleRemove() {
        removePlantFromCollection(collectionPlant.id);
        navigate("/currentCollection");
    }

    return (
        <main id="plantDetails">
            <Button innerText="Back" onClick={() => navigate(-1)} className="back-btn" />
            <img
                src={`${import.meta.env.BASE_URL}images/plants/${collectionPlant.plant.imagePath}`}
                alt={altFromFileName(collectionPlant.plant.imagePath)}
                className="details-page-image"
            />
            <PageTitle title={collectionPlant.plant.name} />
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
                <div className="cost-input-wrapper">
                    <span className="cost-prefix">$</span>
                    <input
                        id="cost"
                        type="text"
                        name="cost"
                        value={detailsData.cost}
                        disabled={!isEditing}
                        onChange={handleChange}
                    />
                </div>
                <label htmlFor="nickname">Nickname:</label>
                <div className="field-with-checkbox">
                    <input
                        id="nickname"
                        type="text"
                        name="nickname"
                        value={detailsData.nickname}
                        disabled={!isEditing}
                        onChange={handleChange}
                    />
                    <label className="show-on-card-label">
                        <input
                            type="checkbox"
                            name="showNickname"
                            checked={detailsData.showNickname}
                            disabled={!isEditing}
                            onChange={handleChange}
                        />
                        Show on card
                    </label>
                </div>
                <label htmlFor="location">Location:</label>
                <div className="field-with-checkbox">
                    <input
                        id="location"
                        type="text"
                        name="location"
                        value={detailsData.location}
                        disabled={!isEditing}
                        onChange={handleChange}
                    />
                    <label className="show-on-card-label">
                        <input
                            type="checkbox"
                            name="showLocation"
                            checked={detailsData.showLocation}
                            disabled={!isEditing}
                            onChange={handleChange}
                        />
                        Show on card
                    </label>
                </div>
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
            <CareInstructions plant={collectionPlant.plant} />
            <SectionDivider />
            <ProgressGallery collectionPlantId={collectionPlant.id} />
            <SectionDivider />
            <div className="remove-btn-container">
                <Button
                    innerText="Remove Plant"
                    icon={faTrashCan}
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
                message={`Are you sure you want to remove the ${collectionPlant.plant.name} from your collection?`}
                confirmText="Remove Plant"
                cancelText="Cancel"
                iconClassName="modal-yellow-warning-icon"
                confirmButtonClassName="remove-btn"
                icon={faTriangleExclamation}
            />
        </main>
    );
}
