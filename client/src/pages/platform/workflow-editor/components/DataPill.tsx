import {Tooltip, TooltipContent, TooltipTrigger} from '@/components/ui/tooltip';
import useWorkflowNodeDetailsPanelStore from '@/pages/platform/workflow-editor/stores/useWorkflowNodeDetailsPanelStore';
import getNestedObject from '@/pages/platform/workflow-editor/utils/getNestedObject';
import {TYPE_ICONS} from '@/shared/typeIcons';
import {PropertyAllType} from '@/shared/types';
import {Editor} from '@tiptap/react';
import resolvePath from 'object-resolve-path';
import {MouseEvent} from 'react';
import {twMerge} from 'tailwind-merge';

import {encodePath, transformPathForObjectAccess, transformValueForObjectAccess} from '../utils/encodingUtils';

interface DataPillProps {
    componentIcon?: string;
    workflowNodeName: string;
    onClick?: (event: MouseEvent<HTMLDivElement>) => void;
    parentProperty?: PropertyAllType;
    property?: PropertyAllType;
    path?: string;
    root?: boolean;
    /* eslint-disable  @typescript-eslint/no-explicit-any */
    sampleOutput?: any;
}

const DataPill = ({
    componentIcon,
    parentProperty,
    path,
    property,
    root = false,
    sampleOutput,
    workflowNodeName,
}: DataPillProps) => {
    const {currentComponent, focusedInput} = useWorkflowNodeDetailsPanelStore();

    const mentionInput: Editor | null = focusedInput;

    const subProperties = property?.properties || property?.items;

    if (!property?.name && property?.controlType === 'ARRAY_BUILDER') {
        property.name = '[index]';
    }

    const handleDataPillClick = (
        workflowNodeName: string,
        propertyName?: string,
        parentPropertyName?: string,
        path?: string
    ) => {
        if (!mentionInput) {
            return;
        }

        const dataPillName = parentPropertyName
            ? `${parentPropertyName}.${propertyName}`
            : `${propertyName || workflowNodeName}`;

        const value = propertyName
            ? `${workflowNodeName}.${(path || dataPillName).replaceAll('/', '.').replaceAll('.[index]', '[0]')}`
            : workflowNodeName;

        const parameters = currentComponent?.parameters || {};

        if (Object.keys(parameters).length) {
            const attributes = mentionInput.view.props.attributes as {[name: string]: string};

            const encodedPath = encodePath(attributes.path);

            const path = transformPathForObjectAccess(encodedPath);

            const paramValue = resolvePath(parameters, path);

            if (attributes.type !== 'STRING' && paramValue) {
                return;
            }
        }

        mentionInput
            .chain()
            .focus()
            .insertContent({
                attrs: {
                    id: transformValueForObjectAccess(value),
                },
                type: 'mention',
            })
            .run();
    };

    const getSubPropertyPath = (subPropertyName = '[index]') =>
        path ? `${path}/${subPropertyName}` : `${property?.name || '[index]'}/${subPropertyName}`;

    if (root) {
        return (
            <div className="flex items-center space-x-2">
                <div
                    className={twMerge(
                        'inline-flex cursor-pointer items-center space-x-2 rounded-full border bg-surface-neutral-secondary px-2 py-0.5 text-sm hover:bg-surface-main',
                        !mentionInput && 'cursor-not-allowed'
                    )}
                    draggable
                    onClick={() => handleDataPillClick(workflowNodeName)}
                >
                    <span className="mr-2" title={property?.type}>
                        {TYPE_ICONS[property?.type as keyof typeof TYPE_ICONS]}
                    </span>

                    <span>{workflowNodeName}</span>
                </div>

                {sampleOutput && typeof sampleOutput !== 'object' && (
                    <div className="flex-1 text-xs text-muted-foreground">{sampleOutput}</div>
                )}
            </div>
        );
    }

    return (
        <li
            className={twMerge(
                'mr-auto',
                subProperties?.length &&
                    'flex flex-col space-y-2 border-0 bg-transparent p-0 hover:cursor-default hover:bg-transparent'
            )}
        >
            <Tooltip>
                <TooltipTrigger asChild>
                    <div
                        className={twMerge(
                            'mr-auto flex cursor-pointer items-center rounded-full border bg-surface-neutral-secondary px-2 py-0.5 text-sm hover:bg-surface-main',
                            !mentionInput && 'cursor-not-allowed'
                        )}
                        data-name={property?.name || workflowNodeName}
                        draggable
                        onClick={() =>
                            handleDataPillClick(
                                workflowNodeName,
                                property?.name || '[index]',
                                parentProperty?.name,
                                path
                            )
                        }
                        onDragStart={(event) => event.dataTransfer.setData('name', property?.name || workflowNodeName)}
                    >
                        {property?.name && (
                            <span className="mr-2" title={property?.type}>
                                {TYPE_ICONS[property?.type as keyof typeof TYPE_ICONS]}
                            </span>
                        )}

                        {!property?.name && (
                            <span className="mr-2" title={property?.type}>
                                {TYPE_ICONS.INTEGER}
                            </span>
                        )}

                        {property?.name || '[index]'}
                    </div>
                </TooltipTrigger>

                {property?.description && (
                    <TooltipContent className="mr-2 max-w-72 whitespace-normal break-normal">
                        <span className="block">{property.description}</span>
                    </TooltipContent>
                )}
            </Tooltip>

            {!!subProperties?.length && (
                <ul className="mt-2 flex flex-col space-y-2 border-l border-l-border/50 pl-4">
                    {subProperties?.map((subProperty, index) => {
                        let sampleValue;

                        if (typeof sampleOutput === 'object') {
                            sampleValue = getNestedObject(
                                sampleOutput,
                                `${getSubPropertyPath(subProperty.name).replaceAll('/', '.')}`
                            );
                        } else {
                            sampleValue = sampleOutput;
                        }

                        if (typeof sampleValue === 'string') {
                            sampleValue =
                                (sampleValue as string).substring(0, 27) +
                                ((sampleValue as string).length > 27 ? '...' : '');
                        }

                        return (
                            <div
                                className="flex items-center space-x-2"
                                key={`${workflowNodeName}-${subProperty.name}-${index}`}
                            >
                                <DataPill
                                    componentIcon={componentIcon}
                                    parentProperty={property}
                                    path={getSubPropertyPath(subProperty.name)}
                                    property={subProperty}
                                    sampleOutput={sampleOutput}
                                    workflowNodeName={workflowNodeName}
                                />

                                {sampleValue === null && (
                                    <span className="flex-1 truncate text-xs text-muted-foreground">null</span>
                                )}

                                {(sampleValue || sampleValue === 0 || sampleValue === false) &&
                                    typeof sampleValue !== 'object' && (
                                        <div className="flex-1 text-xs text-muted-foreground">
                                            {sampleValue === true
                                                ? 'true'
                                                : sampleValue === false
                                                  ? false
                                                  : sampleValue}
                                        </div>
                                    )}
                            </div>
                        );
                    })}
                </ul>
            )}
        </li>
    );
};

export default DataPill;
